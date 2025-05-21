package com.project.myapplication.repository

import com.project.myapplication.ApiService
import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.util.Result
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getDashboardData(request: String): Result<DashboardResponse> {
        return try {
            val response = apiService.getDataByKey(request)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                //Result.Error(Exception("Fetch failed: ${response.code()}"))
                Result.Error(Exception("Error occurred while fetching data."))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
