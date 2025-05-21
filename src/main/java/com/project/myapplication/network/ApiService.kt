package com.project.myapplication
import com.project.myapplication.models.DashboardResponse
import com.project.myapplication.models.LoginRequest
import com.project.myapplication.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {
    //@POST
    //suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @POST
    suspend fun login(
        @Url url: String,
        @Body request: LoginRequest
    ): Response<LoginResponse>


    @GET("dashboard/{keypass}")
    suspend fun getDataByKey(@Path("keypass") keypass: String): Response<DashboardResponse>
}