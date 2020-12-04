package ru.geekbrains.AndroidKotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_note.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.mapToColor
import ru.geekbrains.AndroidKotlin.databinding.FragmentAddNoteBinding
import ru.geekbrains.AndroidKotlin.presentation.main.NoteViewModel

class AddNoteFragment : Fragment() {

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

    private var _binding: FragmentAddNoteBinding? = null
    private val binding: FragmentAddNoteBinding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        return binding.root
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
            binding.titleEt.setText(it.title)
            binding.bodyEt.setText(it.note)
        }

        binding.titleEt.addTextChangedListener {
            viewModel.updateTitle(it?.toString() ?: "")
        }
        binding.bodyEt.addTextChangedListener {
            viewModel.updateNote(it?.toString() ?: "")
        }

        onCreatedCustom()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCreatedCustom() {
        togglePalette().let { true }
        colorPicker.onColorClickListener = {
           viewModel.setColor(it)
            context?.let { cont -> binding.bodyEt.background.setTint(it.mapToColor(cont)) }
        }
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open ()
        }
    }

    // Возврат обратно
    private fun setOkButton() {
        binding.okId.setOnClickListener {
            viewModel.update()
            getActivity()?.onBackPressed()
        }
    }

    // Возврат обратно
    private fun setDeleteButton() {
        binding.deleteId.setOnClickListener {
            viewModel.delete()
            getActivity()?.onBackPressed()
        }
    }

}


