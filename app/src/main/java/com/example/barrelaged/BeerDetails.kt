package com.example.barrelaged

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso


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

        //all interactive items (clickable)
        val show = findViewById<TextView>(R.id.showdescription)


        //set image
        val image_url = "https://imgur.com/bjUWeQP.png"
        Picasso.get().load(image_url).into(image)

        //set description
        val descriptionText = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sitesIt is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites"
        description.text = descriptionText

        //set abv
        val abvText = "ABV: " + " 12%"
        abv.text = abvText

        //set firstbrewed
        val firstbrewedText = "First brewed: " + "04/2008"
        firstbrewed.text = firstbrewedText

        //set foodpairing
        val foodpairingText = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using"
        foodpairing.text = foodpairingText

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