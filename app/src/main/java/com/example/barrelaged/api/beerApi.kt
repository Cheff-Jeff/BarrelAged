package com.example.barrelaged.api

import com.example.barrelaged.modals.beer.beer
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface beerApi {
    //api endpoint
    @GET("beers/random")
    suspend fun getRandomBeer(): Response<List<beer>>
}