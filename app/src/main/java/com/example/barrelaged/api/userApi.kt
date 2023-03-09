package com.example.barrelaged.api

import com.example.barrelaged.modals.BiomettricDto
import com.example.barrelaged.modals.saveBeerDto
import com.example.barrelaged.modals.user
import com.example.barrelaged.modals.userDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface userApi {
    //api endpoint
    @GET("User")
    suspend fun getUsers(): Response<List<user>>
    @POST("User/Login")
    suspend fun login(@Body userDto: userDto?): Response<user>
    @POST("User")
    suspend fun signUp(@Body userDto: userDto?): Response<user>

    @POST("User/NewFingerAuth")
    suspend fun signUpFinger(@Body biometricDto: BiomettricDto): Response<Int>

    @POST("User/FingerAuth")
    suspend fun fingerLogin(@Body biometricDto: BiomettricDto): Response<user>

    @Multipart
    @POST("Beer/uploadimage")
    suspend fun uploadFile(@Part body: MultipartBody.Part)
}