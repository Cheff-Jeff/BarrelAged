package com.example.barrelaged

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.databinding.ActivityHomeBinding
import com.example.barrelaged.modals.dayModals.dayAdapter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var sharedPreferences: SharedPreferences
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = window
        window.statusBarColor = resources.getColor(R.color.toolBar, null)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences("User", MODE_PRIVATE)

        GlobalScope.launch(Dispatchers.Main) {
            val dayOverview = apiCalls().getDayOverview(sharedPreferences.getInt("UserId", 1))
            if(dayOverview != null){
                val adapter = dayAdapter(dayOverview, sharedPreferences.getInt("UserId", 1))
                binding.rvDays.adapter = adapter
                binding.rvDays.layoutManager = LinearLayoutManager(this@Home)
            }
        }

        binding.iconButton.setOnClickListener {
            startActivity(Intent(this, AddBeer::class.java))
        }
    }
}