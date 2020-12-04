package ru.geekbrains.AndroidKotlin.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.geekbrains.AndroidKotlin.R
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.mapToColor
import ru.geekbrains.AndroidKotlin.presentation.main.NoteViewModel

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    companion object {
        const val NOTE_KEY = "Note"

        fun create(note: Note? = null): AddNoteFragment {

            val fragment = AddNoteFragment()
            val arguments = Bundle()
            arguments.putParcelable(NOTE_KEY, note)
            fragment.arguments = arguments

            return fragment
        }
    }

    private val note: Note? by lazy(LazyThreadSafetyMode.NONE) { arguments?.getParcelable(NOTE_KEY) }

    // Подключаем  ViewModel
    private val viewModel by viewModel<NoteViewModel> {
        parametersOf(note)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOkButton()
        setDeleteButton()

        // Заполняем поля данными
        viewModel.note?.let {
            titleEt.setText(it.title)
            bodyEt.setText(it.note)
        }

        titleEt.addTextChangedListener {
            viewModel.updateTitle(it?.toString() ?: "")
        }
        bodyEt.addTextChangedListener {
            viewModel.updateNote(it?.toString() ?: "")
        }

        onCreatedCustom()
    }

    private fun onCreatedCustom() {
        togglePalette().let { true }
        colorPicker.onColorClickListener = {
           viewModel.setColor(it)
            context?.let { cont -> bodyEt.background.setTint(it.mapToColor(cont)) }
        }
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker. open ()
        }
    }

    // Возврат обратно
    private fun setOkButton() {
        ok_id.setOnClickListener {
            viewModel.update()
            getActivity()?.onBackPressed()
        }
    }

    // Возврат обратно
    private fun setDeleteButton() {
        delete_id.setOnClickListener {
            viewModel.delete()
            getActivity()?.onBackPressed()
        }
    }

}


