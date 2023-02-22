package com.example.barrelaged

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
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
        }
    }
}