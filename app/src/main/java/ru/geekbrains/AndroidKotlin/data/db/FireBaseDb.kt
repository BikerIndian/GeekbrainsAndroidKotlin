package ru.geekbrains.AndroidKotlin.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesResult
import ru.geekbrains.AndroidKotlin.errors.NoAuthException
import ru.geekbrains.AndroidKotlin.model.User

private const val NOTES_COLLECTION = "notes" // Ключ коллекции
private const val USERS_COLLECTION = "users" // Ключ для коллекции пользователей
const val TAG = "FireStoreDatabase" // Тэг для Log

class FireBaseDb(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : RemoteDataProvider {

    private val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun selectAll(): LiveData<NotesResult> =
            MutableLiveData<NotesResult>().apply {
                try {
                    getUserNotesCollection().addSnapshotListener { snapshot, e ->
                        value = e?.let { NotesResult.Error(it) }
                                ?: snapshot?.let {
                                    val notes = it.documents.map {
                                        it.toObject(Note::class.java)
                                    }
                                    NotesResult.Success(notes)
                                }
                    }
                } catch (e: Throwable) {
                    value = NotesResult.Error(e)
                }
            }

    override fun insert(note: Note): LiveData<NotesResult> {
        return update(note)
    }

    override fun update(note: Note): LiveData<NotesResult> =
            MutableLiveData<NotesResult>().apply {
                getUserNotesCollection().document(note.id.toString())
                        .set(note).addOnSuccessListener {
                            Log.d(TAG, "update: $note")
                            value = NotesResult.Success(note)
                        }.addOnFailureListener {
                            Log.d(TAG, "update error: $note , message: ${it.message} ")
                            value = NotesResult.Error(it)
                        }
            }

    override fun delete(note: Note): LiveData<NotesResult> =
            MutableLiveData<NotesResult>().apply {
                getUserNotesCollection().document(note.id.toString())
                        .delete().addOnFailureListener {
                            Log.d(TAG, "delete error: $note , message: ${it.message} ")
                            value = NotesResult.Error(it)
                        }
            }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

}
