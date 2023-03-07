package com.example.barrelaged

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barrelaged.databinding.ActivityHomeBinding
import com.example.barrelaged.databinding.ActivityWelcomeBinding
import com.example.barrelaged.modals.dayModals.dayAdapter
import com.example.barrelaged.modals.dayModals.dayModal

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = window
        window.statusBarColor = resources.getColor(R.color.toolBar, null)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val adapter = dayAdapter(tempBeers())
        binding.rvDays.adapter = adapter
        binding.rvDays.layoutManager = LinearLayoutManager(this)
    }

    private fun tempBeers(): List<dayModal>{
        return listOf(
            dayModal(1, "15/02/2023"),
            dayModal(1, "15/02/2023"),
            dayModal(1, "15/02/2023"),
            dayModal(1, "15/02/2023"),
            dayModal(15, "13/02/2023"),
            dayModal(15, "13/02/2023"),
            dayModal(15, "13/02/2023"),
            dayModal(15, "13/02/2023"),
            dayModal(15, "13/02/2023"),
            dayModal(15, "13/02/2023"),
            dayModal(15, "13/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
            dayModal(3, "12/02/2023"),
        )
    }
}