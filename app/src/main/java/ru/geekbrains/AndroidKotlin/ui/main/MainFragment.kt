package ru.geekbrains.AndroidKotlin.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.main_fragment.*
import ru.geekbrains.AndroidKotlin.R
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.presentation.main.MainViewModel
import ru.geekbrains.AndroidKotlin.presentation.main.MainViewState

class MainFragment : Fragment(R.layout.main_fragment) {

    // Подключаем  ViewModel
    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
                MainViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // отправка адаптеру ClickListener для редактирования заметки
        val  adapter = MainAdapter { note -> navigateToNote(note) }

        mainRecycler.adapter = adapter

        viewModel.viewState().observe(viewLifecycleOwner) {
            when (it) {
                is MainViewState.Value -> {
                    adapter.notes = it.notes
                }
                MainViewState.EMPTY -> Unit

                is MainViewState.Error -> {
                    if (it.error != null) {
                        Toast.makeText(requireContext(), "Error: "+it.error.message, Toast.LENGTH_LONG).show()
                    }

                }
            }
        }

        // обработка нажатия на Floating Action Button
        fab.setOnClickListener {
            navigateToCreation()
        }
    }

    // Переход на фрагмент редактирования и передачей данных
    private fun navigateToNote(note: Note) {
        (requireActivity() as MainActivity).navigateTo(AddNoteFragment.create(note))
    }

    // Переход на фрагмент редактирования
    private fun navigateToCreation() {
        (requireActivity() as MainActivity).navigateTo(AddNoteFragment.create(null))
    }

}
