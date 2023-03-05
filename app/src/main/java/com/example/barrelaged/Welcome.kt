package com.example.barrelaged

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.barrelaged.databinding.ActivitySignUpBinding
import com.example.barrelaged.databinding.ActivityWelcomeBinding

class Welcome : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnYes.setOnClickListener {
            val email = intent.getStringExtra("email")
            val password = intent.getStringExtra("password")
            //add finger
        }

        binding.btnNo.setOnClickListener {
            //start home
        }
    }
}