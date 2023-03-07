package com.example.barrelaged.modals.dayModals

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.barrelaged.R

class dayAdapter(private val days: List<dayModal>): RecyclerView.Adapter<dayAdapter.ViewHolder>() {
    private var context: Context? = null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val beers: TextView
        val date: TextView

        init {
            beers = view.findViewById(R.id.dayBeers)
            date = view.findViewById(R.id.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.beer_item, parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.apply {
            val amount = days[position].beers.toString()
            if(days[position].beers > 1){
                beers.text = "$amount beers this day."
            }
            else{
                beers.text = "$amount beer this day."
            }

            date.text = days[position].date
        }
    }

    override fun getItemCount(): Int {
        return days.size;
    }
}