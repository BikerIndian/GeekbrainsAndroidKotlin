package com.supercat.notes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.geekbrains.AndroidKotlin.data.NotesRepository
import ru.geekbrains.AndroidKotlin.errors.NoAuthException
import java.util.concurrent.Executors

class SplashViewModel(private val repository: NotesRepository) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<SplashViewState>()

    init {
        viewModelScope.launch {
            val user = repository.getCurrentUser()

            viewStateLiveData.postValue(
                    if (user != null) {
                        SplashViewState.Auth
                    } else {
                        SplashViewState.Error(error = NoAuthException())
                    }
            )
        }
    }

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData

}
