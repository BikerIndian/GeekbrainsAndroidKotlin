package ru.geekbrains.AndroidKotlin.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.firebase.ui.auth.AuthUI
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
        setToolbar()

        // отправка адаптеру ClickListener для редактирования заметки
        val adapter = MainAdapter { note -> navigateToNote(note) }
        mainRecycler.adapter = adapter

        viewModel.viewState().observe(viewLifecycleOwner) {
            when (it) {
                is MainViewState.Value -> {
                    adapter.notes = it.notes
                }
                MainViewState.EMPTY -> Unit

                is MainViewState.Error -> {
                    if (it.error != null) {
                        Toast.makeText(requireContext(), "Error: ${it.error.message}" , Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        // обработка нажатия на Floating Action Button
        fab.setOnClickListener {
            navigateToCreation()
        }
    }

    private fun setToolbar() {
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.setOnMenuItemClickListener {

            if (it.itemId == R.id.logout) {
                logOut()
                true
            }
            false
        }
    }

    private fun logOut() {
        AuthUI.getInstance()
                .signOut(this.requireContext())
                .addOnCompleteListener {
                    startActivity(Intent(this.requireContext(), SplashActivity::class.java))
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
