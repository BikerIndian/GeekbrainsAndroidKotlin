package ru.geekbrains.AndroidKotlin.presentation.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesRepository
import ru.geekbrains.AndroidKotlin.data.NotesResult

class MainViewModel(repository : NotesRepository) : ViewModel(){
    private val viewStateLiveData: MutableLiveData<MainViewState> =
            MutableLiveData()
    private val repositoryNotes = repository.selectAll()

    private val notesObserver = object : Observer<NotesResult> {
        // Стандартный обсервер LiveData
        override fun onChanged(t: NotesResult?) {
            if (t == null) return
            when (t) {
                is NotesResult.Success<*> -> {
                    viewStateLiveData.value = MainViewState.Value(notes = t.data as List<Note>)
                }
                is NotesResult.Error -> {
                    viewStateLiveData.value = MainViewState.Error(error = t.error)
                }
            }
        }
    }


    fun viewState(): LiveData<MainViewState> = viewStateLiveData

}

