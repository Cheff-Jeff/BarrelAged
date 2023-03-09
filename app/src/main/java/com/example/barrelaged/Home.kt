package com.example.barrelaged

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.databinding.ActivityHomeBinding
import com.example.barrelaged.databinding.ActivityWelcomeBinding
import com.example.barrelaged.modals.dayModals.dayAdapter
import com.example.barrelaged.modals.dayModals.dayDetailModal
import com.example.barrelaged.modals.dayModals.dayModal
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = window
        window.statusBarColor = resources.getColor(R.color.toolBar, null)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        GlobalScope.launch(Dispatchers.Main) {
            val dayOverview = apiCalls().getDayOverview(1)
            if(dayOverview != null){
                val adapter = dayAdapter(dayOverview, tempBeerDetails())
                binding.rvDays.adapter = adapter
                binding.rvDays.layoutManager = LinearLayoutManager(this@Home)
            }
        }
    }

    private fun tempBeerDetails(): List<dayDetailModal>{
        return listOf(
            dayDetailModal("Test", "12/01/2390", "5045NM Tilburg", "Dit is mijn test", "Steing"),
            dayDetailModal("Jeffrey", "12/01/2390", "5045NM Tilburg", "Dit is mijn test", "Steing"),
            dayDetailModal("Duvel", "12/01/2390", "5045NM Tilburg", "Dit is mijn test", "Steing"),
        )
    }
}