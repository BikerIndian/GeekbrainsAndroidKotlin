package ru.geekbrains.AndroidKotlin.presentation.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesRepository
import ru.geekbrains.AndroidKotlin.data.NotesResult
import kotlinx.coroutines.launch

class MainViewModel(repository : NotesRepository) : ViewModel(){
    private val viewStateLiveData: MutableLiveData<MainViewState> =
            MutableLiveData()

    init {
        viewModelScope.launch {
            repository.selectAll().consumeEach {
                when(it){
                    is NotesResult.Success<*> -> {
                        viewStateLiveData.value = MainViewState.Value(notes = it.data as List<Note>)
                    }
                    is NotesResult.Error -> {
                        viewStateLiveData.value = MainViewState.Error(error = it.error)
                    }
                }
            }
        }
    }


    fun viewState(): LiveData<MainViewState> = viewStateLiveData

}

