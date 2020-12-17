package ru.geekbrains.AndroidKotlin.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesResult
import ru.geekbrains.AndroidKotlin.errors.NoAuthException
import ru.geekbrains.AndroidKotlin.model.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val NOTES_COLLECTION = "notes" // Ключ коллекции
private const val USERS_COLLECTION = "users" // Ключ для коллекции пользователей
const val TAG = "FireStoreDatabase" // Тэг для Log

class FireBaseDb(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : RemoteDataProvider {

    private val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun selectAll(): ReceiveChannel<NotesResult> =
            Channel<NotesResult>(Channel.CONFLATED).apply {
                var registration: ListenerRegistration? = null
                try {
                    registration = getUserNotesCollection().addSnapshotListener { snapshot, e ->
                        val value = e?.let { NotesResult.Error(it) }
                                ?: snapshot?.let {
                                    val notes = it.documents.map {
                                        it.toObject(Note::class.java)
                                    }
                                    NotesResult.Success(notes)
                                }
                        value?.let { offer(it) }
                    }
                } catch (e: Throwable) {
                    registration?.remove()
                }
            }


    override suspend fun insert(note: Note): Note {
        return update(note)
    }

    override suspend fun update(note: Note): Note = suspendCoroutine { continuation ->
        try {
                getUserNotesCollection().document(note.id.toString())
                        .set(note).addOnSuccessListener {
                            Log.d(TAG, "update: $note")
                            continuation.resume(note)
                        }.addOnFailureListener {
                            Log.d(TAG, "update error: $note , message: ${it.message} ")
                            continuation.resumeWithException(it)
                        }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
            }

    override suspend fun delete(note: Note):Note = suspendCoroutine { continuation ->
        try {
                getUserNotesCollection().document(note.id.toString())
                        .delete().addOnFailureListener {
                            Log.d(TAG, "delete error: $note , message: ${it.message} ")
                            continuation.resumeWithException(it)
                        }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
            }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

}
