package com.example.barrelaged

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.modals.Animations
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class BeerDetails : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = window
        window.statusBarColor = resources.getColor(R.color.toolBar, null)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_details)

        //all static items
        val description = findViewById<TextView>(R.id.description)
        val abv = findViewById<TextView>(R.id.abv)
        val image = findViewById<ImageView>(R.id.imageView)
        val firstbrewed = findViewById<TextView>(R.id.firstbrewed)
        val foodpairing = findViewById<TextView>(R.id.foodparing)
        val brewerstip = findViewById<TextView>(R.id.brewerstip)
        val beername = findViewById<TextView>(R.id.beername)

        //all interactive items (clickable)
        val show = findViewById<TextView>(R.id.showdescription)
        val showfoodpairing = findViewById<ImageView>(R.id.foodparingdd)
        val showbrewerstip = findViewById<ImageView>(R.id.brewertipdd)
        val facebook = findViewById<Button>(R.id.btnfacebook)
        val twitter = findViewById<Button>(R.id.btntwitter)
        val instagram = findViewById<Button>(R.id.btninstagram)

        val backbtn = findViewById<ImageView>(R.id.btnback)
        val closebtn = findViewById<ImageView>(R.id.btnclose)

        val foodTitle = findViewById<TextView>(R.id.foodparingtitle)
        val brewTitle = findViewById<TextView>(R.id.brewerstiptitle)

        GlobalScope.launch(Dispatchers.Main) {
            val beer = apiCalls().getRandomBeer()
            if (beer != null) {
                Log.d("beer", beer.toString())

                //set title value
                val beertitle = beer[0].name
                beername.text = beertitle

                //set image value
                if(beer[0].image_url != null){
                    val image_url = beer[0].image_url
                    Picasso.get().load(image_url).into(image)
                }

                //set description value
                val descriptionText = beer[0].description
                description.text = descriptionText

                //set abv value
                val abvText = "ABV: " + beer[0].abv + "%"
                abv.text = abvText

                //set firstbrewed value
                val firstbrewedText = "First brewed: " + beer[0].first_brewed
                firstbrewed.text = firstbrewedText

                //set foodpairing value
                val foodpairingText = beer[0].food_pairing
                val sb = StringBuilder()
                foodpairingText.forEach { sb.append(it + System.lineSeparator()) }
                val str = sb.toString()
                foodpairing.text = str

                //set brewerstip value
                val brewerstipText = beer[0].brewers_tips
                brewerstip.text = brewerstipText

            } else {
                Log.d("error", "Oeps")
            }
        }

        //go to previous
        closebtn.setOnClickListener{
            finish()
        }
        backbtn.setOnClickListener{
            finish()
        }

        //navigate to facebook(brewdog)
        facebook.setOnClickListener{
            val url = "https://www.facebook.com/brewdognederland/?locale=nl_NL"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        //navigate to twitter(brewdog)
        twitter.setOnClickListener{
            val url = "https://twitter.com/BrewDog"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        //navigate to instagram(brewdog)
        instagram.setOnClickListener{
            val url = "https://www.instagram.com/brewdogoutpostrdam/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        //layout brewerstip aanpassen (show/hide)
        var brewersOpen: Boolean = false
        var brewersOldHeight: Int = 0;
        val paramsbrewerstip: LayoutParams = brewerstip.layoutParams
        showbrewerstip.setOnClickListener{
            if(brewersOpen){
                brewersOpen = false
                Animations.shrinkTextViewHeight(brewerstip, brewersOldHeight)
                showbrewerstip.animate().rotation(90f).setDuration(250).start()
            }
            else{
                brewersOldHeight = brewerstip.height
                brewersOpen = true
                Animations.growTextViewHeight(brewerstip)
                showbrewerstip.animate().rotation(0f).setDuration(250).start()
            }
            brewerstip.layoutParams = paramsbrewerstip
        }

        brewTitle.setOnClickListener {
            if(brewersOpen){
                brewersOpen = false
                Animations.shrinkTextViewHeight(brewerstip, brewersOldHeight)
                showbrewerstip.animate().rotation(90f).setDuration(250).start()
            }
            else{
                brewersOldHeight = brewerstip.height
                brewersOpen = true
                Animations.growTextViewHeight(brewerstip)
                showbrewerstip.animate().rotation(0f).setDuration(250).start()
            }
            brewerstip.layoutParams = paramsbrewerstip
        }

        //layout foodpairing aanpassen (show/hide)
        var foodOpen: Boolean = false
        var foodOldHeight: Int = 0;
        val paramsfoodpairing: LayoutParams = foodpairing.layoutParams
        showfoodpairing.setOnClickListener{
            if(foodOpen){
                foodOpen = false
                Animations.shrinkTextViewHeight(foodpairing, foodOldHeight)
                showfoodpairing.animate().rotation(90f).setDuration(250).start()
            }
            else{
                foodOldHeight = foodpairing.height
                foodOpen = true
                Animations.growTextViewHeight(foodpairing)
                showfoodpairing.animate().rotation(0f).setDuration(250).start()
            }
            foodpairing.layoutParams = paramsfoodpairing
        }

        foodTitle.setOnClickListener {
            if(foodOpen){
                foodOpen = false
                Animations.shrinkTextViewHeight(foodpairing, foodOldHeight)
                showfoodpairing.animate().rotation(90f).setDuration(250).start()
            }
            else{
                foodOldHeight = foodpairing.height
                foodOpen = true
                Animations.growTextViewHeight(foodpairing)
                showfoodpairing.animate().rotation(0f).setDuration(250).start()
            }
            foodpairing.layoutParams = paramsfoodpairing
        }

        //layout description aanpassen (show/hide)
        val params: LayoutParams = description.layoutParams
        var descOpen: Boolean = false
        var oldHeight: Int = 0;
        show.setOnClickListener{
            if(descOpen){
                descOpen = false
                Animations.shrinkTextViewHeight(description, oldHeight)
                show.text = "more"
            }
            else{
                oldHeight = description.height;
                descOpen = true
                Animations.growTextViewHeight(description)
                show.text = "less"
            }
            description.layoutParams = params
        }
    }
}