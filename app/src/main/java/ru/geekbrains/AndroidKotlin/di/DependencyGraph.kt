package com.supercat.notes.di


import com.supercat.notes.presentation.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesRepository
import ru.geekbrains.AndroidKotlin.data.NotesRepositoryImpl
import ru.geekbrains.AndroidKotlin.data.db.FireBaseDb
import ru.geekbrains.AndroidKotlin.data.db.RemoteDataProvider
import ru.geekbrains.AndroidKotlin.presentation.main.MainViewModel
import ru.geekbrains.AndroidKotlin.presentation.main.NoteViewModel

object DependencyGraph {

    private val repositoryModule by lazy {
        module {
            single { FireBaseDb() } bind RemoteDataProvider::class
            single { NotesRepositoryImpl(get()) } bind NotesRepository::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { MainViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { (note: Note?) -> NoteViewModel(note,get()) }
        }
    }

    val modules: List<Module> = listOf(repositoryModule, viewModelModule)
}
