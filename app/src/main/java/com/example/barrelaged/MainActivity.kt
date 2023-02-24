package com.example.barrelaged

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.api.retrofitHelper
import com.example.barrelaged.api.userApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signUp = findViewById<TextView>(R.id.tvSignUp)
        signUp.setOnClickListener{
            startActivity(Intent(this@MainActivity, SignUp::class.java))
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val email = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tiEmail)
                .editText?.text.toString()
            val password = findViewById<com.google.android.material.textfield.TextInputLayout>(R.id.tiPassword)
                .editText?.text.toString()

            Log.d("email", email)
            Log.d("password", password)

            val title = findViewById<TextView>(R.id.tvTitle)

            val api = apiCalls()
            GlobalScope.launch(Dispatchers.Main) {
                val users = api.getAllUsers()
                if(users != null){
//                    Log.d("Users", users.toString())
                    title.text = users[0].name
                }
                else{
                    title.text = "Woeps"
//                    Log.d("error", "Oeps")
                }
            }
        }
    }
}