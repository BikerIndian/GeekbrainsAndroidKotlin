package ru.geekbrains.AndroidKotlin.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesResult
import ru.geekbrains.AndroidKotlin.errors.NoAuthException


class FireBaseDbTest {

    @Rule
    @JvmField

    val taskExecutorRule = InstantTaskExecutorRule()
    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()
    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()
    private val testNotes = listOf(Note(id = 1), Note(id = 2), Note(id = 3))
    private val provider: FireBaseDb = FireBaseDb(mockDb,mockAuth)

    @Before
    fun setUp() {

        clearMocks(mockCollection, mockDocument1, mockDocument2, mockDocument3)
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""

        every {
            mockDb.collection(any()).document(any()).collection(any())
        } returns mockCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]
    }



    @Test
    fun `should throw NoAuthException if no auth`() {
        every { mockAuth.currentUser } returns null
        var result: Any? = null
        provider.selectAll().observeForever {
            result = (it as NotesResult.Error).error
        }

        Assert.assertTrue(result is NoAuthException)
    }

    @Test
    fun `subscribeToAllNotes returns notes`() {
        var result: List<Note>? = null
        val mockShapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockShapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockCollection.addSnapshotListener(capture(slot)) } returns mockk()

        provider.selectAll().observeForever {
            result = (it as? NotesResult.Success<List<Note>>)?.data
        }
        slot.captured.onEvent(mockShapshot, null)
        Assert.assertEquals(result, testNotes)
    }

    @Test
    fun `subscribeAllNotes return error`() {
        var result: Throwable? = null
        val slot = slot<EventListener<QuerySnapshot>>()
        val testError = mockk<FirebaseFirestoreException>()
        every { mockCollection.addSnapshotListener(capture(slot)) } returns mockk()
        provider.selectAll().observeForever { result = (it as? NotesResult.Error)?.error }
        slot.captured.onEvent( null , testError)
        Assert.assertNotNull(result)
        Assert.assertEquals(testError, result)
    }
}