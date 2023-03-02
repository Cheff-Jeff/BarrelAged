package com.example.barrelaged

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.barrelaged.api.apiCalls
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class BeerDetails : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_details)

        //all static items
        val description = findViewById<TextView>(R.id.description)
        val abv = findViewById<TextView>(R.id.abv)
        val image = findViewById<ImageView>(R.id.imageView)
        val firstbrewed = findViewById<TextView>(R.id.firstbrewed)
        val foodpairing = findViewById<TextView>(R.id.foodparing)
        val brewerstip = findViewById<TextView>(R.id.brewerstip)

        //all interactive items (clickable)
        val show = findViewById<TextView>(R.id.showdescription)

        GlobalScope.launch(Dispatchers.Main) {
            val beer = apiCalls().getRandomBeer()
            if (beer != null) {
                Log.d("beer", beer.toString())

                //set image
                val image_url = beer[0].image_url
                Picasso.get().load(image_url).into(image)

                //set description
                val descriptionText = beer[0].description
                description.text = descriptionText

                //set abv
                val abvText = "ABV: " + beer[0].abv
                abv.text = abvText

                //set firstbrewed
                val firstbrewedText = "First brewed: " + beer[0].first_brewed
                firstbrewed.text = firstbrewedText

                //set foodpairing
                val foodpairingText = beer[0].food_pairing
                val sb = StringBuilder()
                foodpairingText.forEach { sb.append(it + System.lineSeparator()) }
                val str = sb.toString()
                foodpairing.text = str

                //set brewerstip
                val brewerstipText = beer[0].brewers_tips
                brewerstip.text = brewerstipText

            } else {
                Log.d("error", "Oeps")
            }
        }

        //layout description aanpassen (show/hide)
        val params: LayoutParams = description.layoutParams
        show.setOnClickListener{
            if(params.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                params.height = 140
                    show.text = "more"
            }
            else{
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                show.text = "less"
            }
            description.layoutParams = params
        }
    }
}