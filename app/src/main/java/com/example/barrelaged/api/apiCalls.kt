package com.example.barrelaged.api

import android.util.Log
import com.example.barrelaged.modals.BiomettricDto
import com.example.barrelaged.modals.user
import com.example.barrelaged.modals.userDto

class apiCalls {
    private val userApi = retrofitHelper.getInstance().create(userApi::class.java)

    suspend fun getAllUsers(): List<user>?{
        try {
            val result = userApi.getUsers()
            if(result.code() == 200){
                return result.body()
            }
        }catch (e: Exception){
            Log.d("exception", e.toString())
        }
        return null
    }

    suspend fun loginUser(dto: userDto): user?{
        try {
            val result = userApi.login(dto)
            if(result.code() == 200)
            {
                return null
            }
        }catch (e: Exception){
            Log.d("exception", e.toString())
        }
        return null
    }

    suspend fun addFingerprint(dto: BiomettricDto): Int?{
        try {
            val result = userApi.signUpFinger(dto)
            if(result.code() == 204)
            {
                return result.code()
            }
        }catch (e: Exception){
            Log.d("exception", e.toString())
        }
        return null
    }

    suspend fun fingerLogin(dto: BiomettricDto): user?{
        try {
            val result = userApi.fingerLogin(dto)
            if(result.code() == 200)
            {
                return result.body()
            }
        }catch (e: Exception){
            Log.d("exception", e.toString())
        }
        return null
    }

    suspend fun signUpCall(dto: userDto): String?{
        try {
            val result = userApi.signUp(dto)
            print(result)
            if(result.code() == 201)
            {
                return "done"
            }
        }catch (e: Exception){
            Log.d("exception", e.toString())
        }
        return null
    }
}