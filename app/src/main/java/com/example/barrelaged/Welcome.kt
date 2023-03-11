package com.example.barrelaged

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.biometric.biometricHelper
import com.example.barrelaged.databinding.ActivityWelcomeBinding
import com.example.barrelaged.modals.userDto
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Welcome : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("CommitPrefEdits")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences("User", Context.MODE_PRIVATE)

        binding.btnYes.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val email = intent.getStringExtra("email")
                val password = intent.getStringExtra("password")

                if (password != "" && email != "" && password != null && email != null) {
                    val result = biometricHelper.addFingerAuth(password, email)
                    if(result){
                        GlobalScope.launch {
                            val response = apiCalls().loginUser(userDto(
                                Name = "String", Email = email, Password = password, PublicKey = "String"
                            ))
                            if(response != null){
                                val editor = sharedPreferences.edit()
                                editor.putInt("UserId", response.id)
                                editor.putString("UserKey", response.key)
                                editor.putString("UserName", response.name)
                                editor.apply()
                                startActivity(
                                    Intent(this@Welcome, Home::class.java))
                            }
                        }
                    }
                }
            }
        }

        binding.btnNo.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val email = intent.getStringExtra("email")
                val password = intent.getStringExtra("password")

                val response = apiCalls().loginUser(userDto(
                    Name = "String", Email = email.toString(), Password = password.toString(), PublicKey = "String"
                ))
                if(response != null){
                    val editor = sharedPreferences.edit()
                    editor.putInt("UserId", response.id)
                    editor.putString("UserKey", response.key)
                    editor.putString("UserName", response.name)
                    editor.apply()
                    startActivity(
                        Intent(this@Welcome, Home::class.java))
                }
            }
        }
    }
}