package ru.geekbrains.AndroidKotlin.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.db.FireBaseDb
import ru.geekbrains.AndroidKotlin.data.db.ResultFireBaseDb

class MainViewModel : ViewModel() {
    private val viewStateLiveData: MutableLiveData<MainViewState> =
            MutableLiveData()
    private val repositoryNotes = FireBaseDb().selectAll()

    private val notesObserver = object : Observer<ResultFireBaseDb> {
        // Стандартный обсервер LiveData
        override fun onChanged(t: ResultFireBaseDb?) {
            if (t == null) return
            when (t) {
                is ResultFireBaseDb.Success<*> -> {
                    viewStateLiveData.value = MainViewState.Value(notes = t.data as List<Note>)
                }
                is ResultFireBaseDb.Error -> {
                    //  viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }

    init {
        // viewStateLiveData.value = MainViewState.Value(NotesRepositoryImpl.getAllNotes())
        // подписываемся на LiveData
        repositoryNotes.observeForever(notesObserver)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

    override fun onCleared() {
        // отписка от LiveData
        repositoryNotes.removeObserver(notesObserver)
    }
}

