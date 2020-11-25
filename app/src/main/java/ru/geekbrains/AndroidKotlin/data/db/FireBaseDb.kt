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
    private val result = MutableLiveData<List<Note>>()

    override fun selectAll(): LiveData<ResultFireBaseDb> {
        val result = MutableLiveData<ResultFireBaseDb>()
        notesReference.addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(snapshot: QuerySnapshot?, e:
            FirebaseFirestoreException?) {
                if (e != null) {
                    Log.e(TAG, "Observe note exception:$e")
                    result.value = ResultFireBaseDb.Error(e)
                } else if (snapshot != null) {
                    val notes = mutableListOf<Note>()
                    for (doc: QueryDocumentSnapshot in snapshot) {
                        notes.add(doc.toObject(Note::class.java))
                    }
                    //result.value = NoteResult.Success(notes)
                    Log.e(TAG, "NoteResult: $notes")
                    result.value = ResultFireBaseDb.Success(notes)
                }
            }
        })
        return result
    }

    override fun selectById(id: String): LiveData<ResultFireBaseDb> {
        TODO("Not yet implemented")
    }

    override fun insert(note: Note): LiveData<ResultFireBaseDb> {
        return update(note)
    }

    override fun update(newNote: Note): LiveData<ResultFireBaseDb> {
        val result = MutableLiveData<ResultFireBaseDb>()
        notesReference.document(newNote.id.toString())
                .set(newNote).addOnSuccessListener(
                        object : OnSuccessListener<Void> {
                            override fun onSuccess(p0: Void?) {
                                Log.d(TAG, "update: $newNote")
                                result.value = ResultFireBaseDb.Success(newNote)
                            }
                        }).addOnFailureListener {
                    object : OnFailureListener {
                        override fun onFailure(p0: Exception) {
                            Log.d(TAG, "update error: $newNote , message: ${p0.message} ")
                            result.value = ResultFireBaseDb.Error(p0)
                        }
                    }
                }
        return result
    }

    override fun delete(note: Note): LiveData<ResultFireBaseDb> {
        val result = MutableLiveData<ResultFireBaseDb>()
        notesReference.document(note.id.toString())
                .delete().addOnFailureListener {
                    object : OnFailureListener {
                        override fun onFailure(p0: Exception) {
                            Log.d(TAG, "update error: $note , message: ${p0.message} ")
                            result.value = ResultFireBaseDb.Error(p0)
                        }
                    }
                }
        return result
    }


}