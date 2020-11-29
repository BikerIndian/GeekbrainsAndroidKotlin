package ru.geekbrains.AndroidKotlin.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import ru.geekbrains.AndroidKotlin.data.Note

private const val NOTES_COLLECTION = "notes" // Ключ коллекции
const val TAG = "FireStoreDatabase" // Тэг для Log

class FireBaseDb : RemoteDataProvider {

    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection(NOTES_COLLECTION)

    override fun selectAll(): LiveData<ResultFireBaseDb> =
            MutableLiveData<ResultFireBaseDb>().apply {
                notesReference.addSnapshotListener { snapshot, e ->
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
                notesReference.document(note.id.toString())
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
                notesReference.document(note.id.toString())
                        .delete().addOnFailureListener {
                            Log.d(TAG, "delete error: $note , message: ${it.message} ")
                            value = ResultFireBaseDb.Error(it)
                        }
            }


}