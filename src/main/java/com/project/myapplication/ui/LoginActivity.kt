package com.project.myapplication.ui

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.myapplication.R
import com.project.myapplication.models.LoginResponse
import com.project.myapplication.util.Result
import com.project.myapplication.viewmodel.DashboardViewModel
import com.project.myapplication.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val usernameLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.usernameLayout)
        val passwordLayout = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.passwordLayout)
        var dynamicEndpoint = ""
        val options = listOf("Sydney", "Footscray", "ORT")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)

        val layout = findViewById<TextInputLayout>(R.id.dropdownLayout)
        layout.defaultHintTextColor = ColorStateList.valueOf(Color.WHITE)

        val dropdown = findViewById<AutoCompleteTextView>(R.id.dropdownInput)
        dropdown.setDropDownBackgroundResource(android.R.color.white)
        dropdown.setAdapter(adapter)

        // Show dropdown on click or focus
        dropdown.setOnClickListener {
            dropdown.showDropDown()
        }
        dropdown.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                dropdown.showDropDown()
            }
        }

        dropdown.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()

            dynamicEndpoint = when (selectedItem) {
                "Sydney" -> "/sydney/auth"
                "Footscray" -> "/footscray/auth"
                "ORT" -> "/ort/auth"
                else -> ""
            }


            Toast.makeText(this, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }


        // Observe login result
        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    handleResponse(result.data)
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    //Toast.makeText(this, result.exception.message ?: "Unknown error", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Login button click
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            var isValid = true
            if (username.isEmpty()) {
                usernameLayout.error = "Username cannot be empty"
                isValid = false
            } else {
                usernameLayout.error = null
            }

            if (password.isEmpty()) {
                passwordLayout.error = "Password cannot be empty"
                isValid = false
            } else {
                passwordLayout.error = null
            }

            if (isValid) {
                if (isInternetAvailable(this)) {
                    loginViewModel.login(dynamicEndpoint, username, password)
                } else {
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleResponse(response: LoginResponse) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("keypass", response.keypass)
        startActivity(intent)
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
