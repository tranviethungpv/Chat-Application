package com.example.chatapp1.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.chatapp1.User
import com.example.chatapp1.data.repository.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var appRepository: AppRepository

    @Mock
    private lateinit var userObserver: Observer<User>

    @Mock
    private lateinit var listUserObserver: Observer<List<User>>

    private lateinit var userViewModel: UserViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        userViewModel = UserViewModel(appRepository)
    }

    @Test
    fun `test getUser function`() = runBlockingTest {
        val userId = 1
        val user = User(userId, "Test User", "testuser@example.com")
        whenever(appRepository.getUserById(userId)).thenReturn(user)

        userViewModel.getUserById(userId).observeForever(userObserver)

        verify(appRepository).getUserById(userId)
        verify(userObserver).onChanged(user)
    }

    @Test
    fun `test getUsers function`() = runBlockingTest {
        val listUser = listOf(User(0, "Test User", "testuser@example.com"))
        whenever(appRepository.getAllUsers()).thenReturn(listUser)

        userViewModel.getUsers()
        userViewModel.users.observeForever(listUserObserver)

        verify(appRepository).getAllUsers()
        verify(listUserObserver).onChanged(listUser)
    }
}