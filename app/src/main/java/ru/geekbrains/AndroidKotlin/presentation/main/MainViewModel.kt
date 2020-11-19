package ru.geekbrains.AndroidKotlin.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supercat.notes.data.NotesRepositoryImpl

class MainViewModel : ViewModel() {
    private val viewStateLiveData: MutableLiveData<MainViewState> =
            MutableLiveData()
    init {
        viewStateLiveData.value = MainViewState.Value(NotesRepositoryImpl.getAllNotes())
    }
    fun viewState (): LiveData<MainViewState> = viewStateLiveData
}

