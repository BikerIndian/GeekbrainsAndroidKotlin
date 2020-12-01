package com.supercat.notes.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.AndroidKotlin.data.db.FireBaseDb
import ru.geekbrains.AndroidKotlin.data.db.RemoteDataProvider
import ru.geekbrains.AndroidKotlin.errors.NoAuthException
import java.util.concurrent.Executors

class SplashViewModel(private val repository: RemoteDataProvider) : ViewModel() {
    private val viewStateLiveData = MutableLiveData<SplashViewState>()

    init {
        Executors.newSingleThreadExecutor()
            .submit {
                requestUser()
            }
    }

    fun observeViewState(): LiveData<SplashViewState> = viewStateLiveData

    private fun requestUser() {
        val user = repository.getCurrentUser()

        viewStateLiveData.postValue(
            if (user != null) {
                //Log.d("","")
                SplashViewState.Auth
            } else {
                SplashViewState.Error(error = NoAuthException())
            }
        )
    }

}
