package com.example.chatapp1.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.chatapp1.Conversation
import com.example.chatapp1.Message
import com.example.chatapp1.MessageWithUsersAndConversation
import com.example.chatapp1.User
import com.example.chatapp1.data.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MessageViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    // The repository to be mocked using Mockito
    private val appRepository = mock(AppRepository::class.java)

    @Mock
    // The ViewModel to be tested
    private lateinit var messageViewModel: MessageViewModel

    @Mock
    // The observer to be used in the tests
    private val listMessageWithUserObserver = mock<Observer<List<MessageWithUsersAndConversation>>>()


    @Before
    fun setUp() {
        // Set the main dispatcher to testDispatcher
        Dispatchers.setMain(testDispatcher)

        // Initialize the ViewModel
        messageViewModel = MessageViewModel(appRepository)
    }

    @After
    fun tearDown() {
        // Reset main dispatcher to the original
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testInsertMessage() = runBlocking {
        val message = Message(1, 1, 1, "Danny", 4) // create a message
        val expected = true

        // When insertMessage is called on appRepository, return a non-negative number
        `when`(appRepository.insertMessage(message)).thenReturn(1L)

        // Call the function to be tested
        val result = messageViewModel.insertMessage(message)

        // Verify that the function in the repository was called
        verify(appRepository).insertMessage(message)

        // Assert that the result is as expected
        assertEquals(expected, result)
    }

    @Test
    fun `test getMessagesWithUsersAndConversation function`() = runBlockingTest {
        val conversationId = 1
        val messageWithUsersAndConversation = listOf(
            MessageWithUsersAndConversation(
                Message(1, 1, 1, "Danny", 4),
                User(1, "Test User", "testuser@example.com"),
                User(2, "Test User 2", "testuser2@example.com"),
                Conversation(1, 1, 2)
            )
        )
        whenever(
            appRepository
                .getMessagesWithUsersAndConversation(conversationId)
        ).thenReturn(messageWithUsersAndConversation)

        messageViewModel.messageWithUsersAndConversation.observeForever(listMessageWithUserObserver)
        verify(appRepository).getMessagesWithUsersAndConversation(conversationId)
        verify(listMessageWithUserObserver).onChanged(messageWithUsersAndConversation)
    }
}


