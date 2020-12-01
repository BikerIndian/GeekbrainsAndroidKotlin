package ru.geekbrains.AndroidKotlin.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.errors.NoAuthException
import ru.geekbrains.AndroidKotlin.model.User

private const val NOTES_COLLECTION = "notes" // Ключ коллекции
private const val USERS_COLLECTION = "users" // Ключ для коллекции пользователей
const val TAG = "FireStoreDatabase" // Тэг для Log

class FireBaseDb : RemoteDataProvider {

    private val db = FirebaseFirestore.getInstance()
   // private val notesReference = getUserNotesCollection()

    private val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    override fun selectAll(): LiveData<ResultFireBaseDb> =
            MutableLiveData<ResultFireBaseDb>().apply {
                getUserNotesCollection().addSnapshotListener { snapshot, e ->
                    value = e?.let { ResultFireBaseDb.Error(it) }
                            ?: snapshot?.let {
                                val notes = it.documents.map {
                                    it.toObject(Note:: class . java ) }
                                ResultFireBaseDb.Success(notes)
                            }
                }
            }

    override fun insert(note: Note): LiveData<ResultFireBaseDb> {
        return update(note)
    }

    override fun update (note: Note ): LiveData<ResultFireBaseDb> =
            MutableLiveData<ResultFireBaseDb>().apply {
                getUserNotesCollection().document(note.id.toString())
                        .set (note).addOnSuccessListener {
                            Log.d(TAG, "update: $note")
                            value = ResultFireBaseDb.Success(note)
                        }.addOnFailureListener {
                            Log.d(TAG, "update error: $note , message: ${it.message} ")
                            value = ResultFireBaseDb.Error(it)
                        }
            }

    override fun delete (note: Note ): LiveData<ResultFireBaseDb> =
            MutableLiveData<ResultFireBaseDb>().apply {
                getUserNotesCollection().document(note.id.toString())
                        .delete().addOnFailureListener {
                            Log.d(TAG, "delete error: $note , message: ${it.message} ")
                            value = ResultFireBaseDb.Error(it)
                        }
            }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

}
val notesRepository: RemoteDataProvider by lazy { FireBaseDb() }