package com.project.myapplication.viewmodel

import com.project.myapplication.util.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse
import com.project.myapplication.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    fun login(dynamicEndpoint: String, username: String, password: String) {
        val request = LoginRequest(username, password)
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            _loginResult.value = repository.login(dynamicEndpoint, request)
        }
    }
}
