package com.project.myapplication

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse
import com.project.myapplication.ui.DashboardActivity
import com.project.myapplication.ui.LoginActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast
import retrofit2.Call
import retrofit2.Response

@Config(manifest = Config.NONE)
@RunWith(MockitoJUnitRunner::class)
class LoginActivityTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var mockCall: Call<LoginResponse>

    private lateinit var loginActivity: LoginActivity
    private lateinit var loginButton: Button
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var progressBar: ProgressBar

    // To set up the required UI elements and other services
    @Before
    fun setUp() {
        // Initialize LoginActivity with Robolectric
        loginActivity = Robolectric.setupActivity(LoginActivity::class.java)

        // Mock UI elements
        loginButton = loginActivity.findViewById(R.id.loginButton)
        usernameInput = loginActivity.findViewById(R.id.usernameInput)
        passwordInput = loginActivity.findViewById(R.id.passwordInput)
        progressBar = loginActivity.findViewById(R.id.progressBar)

        // Mock ApiService and the login call
        `when`(apiService.login(any<LoginRequest>())).thenReturn(mockCall)
    }

    // To test if the login button performs a click event
    @Test
    fun testLoginButtonClick_shouldCallApi() {
        // Arrange: Set input values
        usernameInput.setText("user")
        passwordInput.setText("pass")

        // Act: Simulate button click
        loginButton.performClick()

        // Verify that the API call is made
        verify(apiService).login(any<LoginRequest>())
    }

    // To test a successful login
    @Test
    fun testHandleResponse_successfulLogin() {
        // Arrange: Mock successful API response
        val response = mock<Response<LoginResponse>>()
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(LoginResponse("keypass"))

        // Act: Call handleResponse method with mock response
        loginActivity.handleResponse(response)

        // Verify the intent to DashboardActivity was triggered
        val intent = Intent(loginActivity, DashboardActivity::class.java)
        intent.putExtra("keypass", "keypass")

        // Assert: Verify Toast for successful login
        verify(loginActivity).startActivity(intent)
    }

    // To test the unsuccessful login
    @Test
    fun testHandleResponse_unsuccessfulLogin() {
        // Arrange: Mock unsuccessful API response
        val response = mock<Response<LoginResponse>>()
        `when`(response.isSuccessful).thenReturn(false)

        // Act: Call handleResponse method with mock response
        loginActivity.handleResponse(response)

        // Assert: Check the latest Toast message
        val latestToast = ShadowToast.getTextOfLatestToast()
        assertEquals("Username or Password incorrect.", latestToast)
    }

    // To test the internet connection
    @Test
    fun testNoInternetConnection_shouldShowToast() {
        // Simulate no internet by mocking the method
        val method = loginActivity.javaClass.getDeclaredMethod("isInternetAvailable", Context::class.java)
        method.isAccessible = true
        `when`(method.invoke(loginActivity, loginActivity.applicationContext)).thenReturn(false)

        // Clear input fields to simulate filled credentials
        usernameInput.setText("test")
        passwordInput.setText("pass")

        // Simulate button click
        loginButton.performClick()

        // Assert
        val latestToast = ShadowToast.getTextOfLatestToast()
        assertEquals("No internet connection", latestToast)
    }
}