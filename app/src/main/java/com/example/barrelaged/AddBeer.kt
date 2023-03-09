package com.example.barrelaged

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.databinding.ActivityAddbeerBinding
import com.example.barrelaged.modals.saveBeerDto
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class AddBeer : AppCompatActivity() {
    private lateinit var binding: ActivityAddbeerBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var geocoder: Geocoder
    lateinit var imageUri: Uri
    lateinit var sharedPreferences: SharedPreferences
    lateinit var resolver: ContentResolver

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = window
        window.statusBarColor = resources.getColor(R.color.toolBar, null)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityAddbeerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = this.getSharedPreferences("User", MODE_PRIVATE)
        resolver = this.contentResolver

//        var currentBeer = ""
//        val items = ArrayList<String>()
//        items.add("item1")
//        items.add("item2")
//        items.add("item3")
//        items.add("item4")
//        items.add("item5")
//
//        val adapterItems = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,items)
//        binding.dropdown.setAdapter(adapterItems)
//
//        binding.dropdown.setOnItemClickListener { adapterView, view, i, l ->
//            currentBeer = adapterView.getItemAtPosition(i).toString()
//            println(currentBeer)
//        }

        //huidige locatie vullen.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getThisLocation()

        //huidige dage vullen.
        getThisDate()

        //button take image
        binding.btnImage.setOnClickListener {
            getThisImage()
//            getImageFromGallary()
        }

        //bier naar api sturen.
        binding.btnsave.setOnClickListener {
            submitBeer()
//            binding.txtbeer.editText?.setText("")
//            binding.txtbeerdescription.editText?.setText("")
        }

        //terug naar vorige pagina
        binding.btncancel.setOnClickListener {
            finish()
        }

        val toolbartext = findViewById<TextView>(R.id.beername)
        toolbartext.text = "Add beer"

        val btnback = findViewById<ImageView>(R.id.btnback)
        val btnclose = findViewById<ImageView>(R.id.btnclose)
        btnback.setOnClickListener {
            finish()
        }
        btnclose.setOnClickListener {
            finish()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun submitBeer() {
        GlobalScope.launch(Dispatchers.Main) {
            val call = apiCalls().saveBeer(
                saveBeerDto(
                    beerDate = binding.txtdate.text.toString(),
                    beerLocation = binding.txtlocation.text.toString(),
                    beerName = binding.txtbeer.editText?.text.toString(),
                    beerDescription = binding.txtbeerdescription.editText?.text.toString(),
                    UserId = sharedPreferences.getInt("UserId", 1),
                )
            )
//            if(call == 200){
//                Snackbar.make(binding.root, "Beer has been added", Snackbar.LENGTH_SHORT).show()
//                startActivity(Intent(this@AddBeer, MainActivity::class.java))
//            }
            Snackbar.make(binding.root, "Beer has been added", Snackbar.LENGTH_SHORT).show()
            startActivity(Intent(this@AddBeer, Home::class.java))
        }
    }

    private fun getThisDate() {
        val calander = Calendar.getInstance().time
        val date = DateFormat.getDateInstance().format(calander)

        binding.txtdate.text = date
    }

    private fun getThisLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                geocoder = Geocoder(this, Locale.getDefault())
                val adress = geocoder.getFromLocation(it.latitude, it.longitude, 1)

                binding.txtlocation.text = adress!![0].getAddressLine(0)
            }
        }
    }

    private fun getThisImage() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                101
            )
        } else {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(i, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var image = data?.getParcelableExtra<Bitmap>("data")
        binding.test.setImageBitmap(image)

        saveToGallery()
        timer()
    }

    private fun saveToGallery() {

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                imageUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            }else{
                imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val imageName = "barrelAged_${System.currentTimeMillis()}.jpg"
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            var uri: Uri = resolver.insert(imageUri, contentValues)!!

            try {
                val bitmapDrawable = binding.test.drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap

                val outputStream = resolver.openOutputStream(Objects.requireNonNull(uri))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                Objects.requireNonNull(outputStream)
                Snackbar.make(binding.root, "Image saved to gallery", Snackbar.LENGTH_SHORT).show()

            }catch (e: Exception){
                Log.d("exception", e.toString())
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 101)
        }
    }

    private fun timer(){
        binding.test.isVisible = true
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long){ }
            override fun onFinish() {
                binding.test.isVisible = false
            }
        }
        timer.start()
    }
}
