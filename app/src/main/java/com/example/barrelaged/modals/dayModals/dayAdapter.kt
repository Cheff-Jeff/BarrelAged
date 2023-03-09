package com.example.barrelaged.modals.dayModals

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barrelaged.R
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.modals.Animations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class dayAdapter(private val days: List<dayOverView>, private val details: List<dayDetailModal>):
    RecyclerView.Adapter<dayAdapter.ViewHolder>() {
    private lateinit var context: Context
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val beers: TextView
        val date: TextView
        val overviewContainer: RelativeLayout
        val detailContainer: RelativeLayout
        val toggle: TextView
        val DetailView: RecyclerView
        val cardContainer: com.google.android.material.card.MaterialCardView

        init {
            beers = view.findViewById(R.id.dayBeers)
            date = view.findViewById(R.id.date)
            overviewContainer = view.findViewById(R.id.dayOverview)
            detailContainer = view.findViewById(R.id.dayDetail)
            toggle = view.findViewById(R.id.link)
            DetailView = view.findViewById(R.id.rvDayDetail)
            cardContainer = view.findViewById(R.id.card)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.beer_item, parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.apply {
            val amount = days[position].amount.toString()
            if(days[position].amount > 1){
                beers.text = "$amount beers this day."
            }
            else{
                beers.text = "$amount beer this day."
            }

            date.text = days[position].date

            toggle.setOnClickListener {
                if(overviewContainer.visibility == View.VISIBLE)
                {

                    GlobalScope.launch(Dispatchers.Main) {
                        val details = apiCalls().getDayDetail(days[position].date)
                        if(details != null){
                            val newAdapter = dayDetailAdapter(details)
                            DetailView.adapter = newAdapter
                            DetailView.layoutManager = LinearLayoutManager(context)
                            toggle.text = "Close"
                        }
                    }
                    overviewContainer.visibility = View.GONE
                    detailContainer.visibility = View.VISIBLE
                }
                else
                {
                    overviewContainer.visibility = View.VISIBLE
                    detailContainer.visibility = View.GONE

                    toggle.text = "View day"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return days.size;
    }
}