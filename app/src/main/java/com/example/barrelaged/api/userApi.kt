package com.example.barrelaged.api

import com.example.barrelaged.modals.user
import com.example.barrelaged.modals.userDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface userApi {
    //api endpoint
    @GET("User")
    suspend fun getUsers(): Response<List<user>>

    @POST("User/Login")
    suspend fun login(@Body userDto: userDto?): Response<userDto?>
}