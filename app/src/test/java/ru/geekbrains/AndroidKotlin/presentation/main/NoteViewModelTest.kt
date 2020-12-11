package ru.geekbrains.AndroidKotlin.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.mockk.*
import org.junit.*
import org.junit.rules.TestRule
import ru.geekbrains.AndroidKotlin.data.Color
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesRepository
import ru.geekbrains.AndroidKotlin.data.NotesResult
import ru.geekbrains.AndroidKotlin.model.User

class NoteViewModelTest {

    private val notesRepositoryMock = mockk<NotesRepository>()
    private lateinit var viewModel: NoteViewModel
    private var _resultLiveData = MutableLiveData<NotesResult>(
            NotesResult.Success(Note())
    )
    private val resultLiveData: LiveData<NotesResult> get() = _resultLiveData

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.getCurrentUser() } returns User("name", "email")
        every { notesRepositoryMock.selectAll() } returns resultLiveData
        every { notesRepositoryMock.update(any()) } just Runs
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Test updateNote`() {
        val testStr = "Test";
        val currentNote = Note(note = "Hello!")

        viewModel = NoteViewModel(currentNote, notesRepositoryMock)
        viewModel.updateNote(testStr)
        Assert.assertEquals(testStr, viewModel.note?.note)
    }

    @Test
    fun `Test updateTitle`() {
        val testStr = "Test";
        val currentNote = Note(note = "Title!")

        viewModel = NoteViewModel(currentNote, notesRepositoryMock)
        viewModel.updateTitle(testStr)
        Assert.assertEquals(testStr, viewModel.note?.title)
    }

    @Test
    fun `Test setColor`() {
        val currentNote = Note(note = "Title!")
        val color = Color.YELLOW
        viewModel = NoteViewModel(currentNote, notesRepositoryMock)
        viewModel.setColor(color)
        Assert.assertEquals(color, viewModel.note?.color)
    }

    @Test
    fun `Test update()`() {
        val testStr = "Test";
        viewModel = NoteViewModel( Note(note = "Title!", title= "asd"), notesRepositoryMock)
        viewModel.updateNote("testStr")
        viewModel.update()

        var slot = slot<Note>()

        verify(exactly = 1) {
            notesRepositoryMock.update(capture(slot))
        }

        Assert.assertEquals("testStr", slot.captured.note)
    }

}