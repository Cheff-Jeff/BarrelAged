package com.example.barrelaged.api

import android.util.Log
import com.example.barrelaged.modals.user
import com.example.barrelaged.modals.userDto
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

    suspend fun loginUser(dto: userDto): Int?{
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
}