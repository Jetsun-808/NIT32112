package com.project.myapplication.repository

import com.project.myapplication.ApiService
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse
import com.project.myapplication.util.Result
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun login(dynamicEndpoint: String, request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = apiService.login(dynamicEndpoint, request)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                //Result.Error(Exception("Login failed: ${response.code()}"))
                Result.Error(Exception("Invalid username or password."))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
