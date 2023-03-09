package com.example.barrelaged.modals.dayModals

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.barrelaged.BeerDetails
import com.example.barrelaged.Home
import com.example.barrelaged.R
import com.example.barrelaged.modals.Animations

class dayDetailAdapter(private val details: List<beerDTO>):
    RecyclerView.Adapter<dayDetailAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val beerName: TextView
        val date: TextView
        val location: TextView
        val description: TextView
        val img: ImageView
        val btnInfo: ImageView
        val toggle1: TextView
        val toggle2: ImageView

        init {
            beerName = view.findViewById(R.id.detailTitle)
            date = view.findViewById(R.id.detailDate)
            location = view.findViewById(R.id.detailLocation)
            description = view.findViewById(R.id.tvMyDescription)
            img = view.findViewById(R.id.myImage)
            btnInfo = view.findViewById(R.id.detailInformation)
            toggle1 = view.findViewById(R.id.descriptionTitle)
            toggle2 = view.findViewById(R.id.descriptionToggle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.day_beer_detail, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            beerName.text = details[position].beerName
            date.text = details[position].beerDate
            location.text = details[position].beerLocation
            description.text = details[position].beerDescription

            btnInfo.setOnClickListener {
                context.startActivity(
                    Intent(context, BeerDetails::class.java)
                )
            }

            toggle1.setOnClickListener {
                if(description.height > 5){
                    Animations.shrinkTextViewHeight(description, 1)
                    toggle2.animate().rotation(90f).setDuration(250).start()
                }
                else{
                    toggle2.animate().rotation(0f).setDuration(250).start()
                    Animations.growTextViewHeight(description)
                }
            }
            toggle2.setOnClickListener {
                if(description.height > 5){
                    Animations.shrinkTextViewHeight(description, 1)
                    toggle2.animate().rotation(90f).setDuration(250).start()
                }
                else{
                    toggle2.animate().rotation(0f).setDuration(250).start()
                    Animations.growTextViewHeight(description)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return details.size
    }
}