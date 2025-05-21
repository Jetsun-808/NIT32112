//package com.project.myapplication.test
//
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.jarjarred.org.antlr.v4.tool.Rule
//import com.project.myapplication.models.LoginRequest
//import com.project.myapplication.models.LoginResponse
//import com.project.myapplication.repository.AuthRepository
//import com.project.myapplication.viewmodel.LoginViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//
//@ExperimentalCoroutinesApi
//class LoginViewModelTest {
//
//    @get:Rule
//    val hiltRule = HiltAndroidRule(this)
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private val repository = mockk<AuthRepository>()
//    private lateinit var viewModel: LoginViewModel
//
//    @Before
//    fun setup() {
//        viewModel = LoginViewModel(repository)
//    }
//
//    @Test
//    fun `login success sets success result`() = runTest {
//        val request = LoginRequest("user", "pass")
//        val response = LoginResponse("token123")
//        coEvery { repository.login(request) } returns Result.Success(response)
//
//        viewModel.login("user", "pass")
//
//        assert(viewModel.loginResult.getOrAwaitValue() is Result.Success)
//    }
//}
