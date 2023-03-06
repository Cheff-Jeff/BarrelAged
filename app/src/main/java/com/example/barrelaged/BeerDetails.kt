package com.example.barrelaged

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
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

        GlobalScope.launch(Dispatchers.Main) {
            val beer = apiCalls().getRandomBeer()
            if (beer != null) {
                Log.d("beer", beer.toString())

                //set title value
                val beertitle = beer[0].name
                beername.text = beertitle

                //set image value
                val image_url = beer[0].image_url
                Picasso.get().load(image_url).into(image)

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
        val paramsbrewerstip: LayoutParams = brewerstip.layoutParams
        showbrewerstip.setOnClickListener{
            if(paramsbrewerstip.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                paramsbrewerstip.height = 96
                showbrewerstip.setRotation(90f)
            }
            else{
                paramsbrewerstip.height = ViewGroup.LayoutParams.WRAP_CONTENT
                showbrewerstip.setRotation(0f)
            }
            brewerstip.layoutParams = paramsbrewerstip
        }

        //layout foodpairing aanpassen (show/hide)
        val paramsfoodpairing: LayoutParams = foodpairing.layoutParams
        showfoodpairing.setOnClickListener{
            if(paramsfoodpairing.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                paramsfoodpairing.height = 96
                showfoodpairing.setRotation(90f)
            }
            else{
                paramsfoodpairing.height = ViewGroup.LayoutParams.WRAP_CONTENT
                showfoodpairing.setRotation(0f)
            }
            foodpairing.layoutParams = paramsfoodpairing
        }


        //layout description aanpassen (show/hide)
        val params: LayoutParams = description.layoutParams
        show.setOnClickListener{
            if(params.height == LayoutParams.WRAP_CONTENT){
                params.height = 134
                    show.text = "more"
            }
            else{
                description.measure(View.MeasureSpec.makeMeasureSpec(description.width - 0, View.MeasureSpec. EXACTLY), View.MeasureSpec.UNSPECIFIED)
                val endHeight = description.measuredHeight

                val heightAnimator = ValueAnimator.ofInt(params.height ,endHeight).setDuration(250);
                heightAnimator.addUpdateListener { animation1 ->
                    val value = animation1.animatedValue as Int
                    description.layoutParams.height = value
                    description.requestLayout()
                }

                val animationSet = AnimatorSet()
                animationSet.interpolator = AccelerateDecelerateInterpolator()
                animationSet.play(heightAnimator);
                animationSet.start()
                show.text = "less"
            }
            description.layoutParams = params
        }
    }
}