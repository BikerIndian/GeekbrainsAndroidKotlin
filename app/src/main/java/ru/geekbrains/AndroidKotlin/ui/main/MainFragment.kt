package ru.geekbrains.AndroidKotlin.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.main_fragment.*
import ru.geekbrains.AndroidKotlin.R
import ru.geekbrains.AndroidKotlin.presentation.main.MainViewModel
import ru.geekbrains.AndroidKotlin.presentation.main.MainViewState

class MainFragment : Fragment(R.layout.main_fragment) {
    lateinit var viewModel: MainViewModel

    private val viewMode by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
                MainViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MainAdapter()

        mainRecycler.adapter = adapter

        // build add jvmTarget = '1.8'
        viewMode.viewState().observe(viewLifecycleOwner) {
            when (it) {
                is MainViewState.Value -> {
                    adapter.notes = it.notes
                }
                MainViewState.EMPTY -> Unit
            }
        }

        // обработка нажатия на Floating Action Button
        fab.setOnClickListener {
            navigateToCreation()
        }
    }

    // Переход на фрагмент редактирования
    private fun navigateToCreation() {
        (requireActivity() as MainActivity).navigateTo(AddNoteFragment.create(null))
    }

}
