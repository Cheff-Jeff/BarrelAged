package com.example.barrelaged.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitHelper {
    val host = "https://i483908.luna.fhict.nl/api/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(host)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}