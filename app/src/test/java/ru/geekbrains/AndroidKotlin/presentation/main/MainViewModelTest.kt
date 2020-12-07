package ru.geekbrains.AndroidKotlin.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.*
import org.junit.*
import org.junit.rules.TestRule
import ru.geekbrains.AndroidKotlin.data.NotesRepository
import ru.geekbrains.AndroidKotlin.data.NotesResult

class MainViewModelTest {
    private val notesRepositoryMock = mockk<NotesRepository>()
    private lateinit var viewModel: MainViewModel
    private val notesLiveData = MutableLiveData<NotesResult>()

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.selectAll() } returns notesLiveData
        viewModel = MainViewModel(notesRepositoryMock)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should call getNotes once`() {
        verify(exactly = 1) { notesRepositoryMock.selectAll() }
    }

    @Test
    fun `Test viewState`() {
        var result: Throwable? = null
        val error = Throwable("error")

        viewModel.viewState().observeForever {
            when (it) {
                is MainViewState.Value -> Unit
                MainViewState.EMPTY -> Unit
                is MainViewState.Error -> if (it.error != null) {
                    result = it.error
                }
            }

            notesLiveData.value = NotesResult.Error(error);
            Assert.assertEquals(result, error)

        }
    }

    // Проверка отписки
    @Test
    fun `should remove observer`() {
        viewModel.onCleared()
        Assert.assertFalse(notesLiveData.hasObservers())
    }
}