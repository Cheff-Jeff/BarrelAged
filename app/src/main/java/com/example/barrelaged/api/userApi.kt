package com.example.barrelaged.api

import com.example.barrelaged.modals.user
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface userApi {
    //api endpoint
    @GET("User")
    suspend fun getUsers(): Response<List<user>>
}