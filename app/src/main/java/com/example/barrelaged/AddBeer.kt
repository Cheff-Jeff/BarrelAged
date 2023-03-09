package com.example.barrelaged

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.loader.content.CursorLoader
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.api.retrofitHelper
import com.example.barrelaged.api.uploadRequestBody
import com.example.barrelaged.api.userApi
import com.example.barrelaged.databinding.ActivityAddbeerBinding
import com.example.barrelaged.modals.saveBeerDto
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.DateFormat
import java.util.*

@Suppress("DEPRECATION")
class AddBeer : AppCompatActivity() {
    private lateinit var binding: ActivityAddbeerBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var geocoder: Geocoder
    lateinit var imageUri: Uri
    private val userApi = retrofitHelper.getLocalInstance().create(userApi::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityAddbeerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //huidige locatie vullen.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getThisLocation()

        //huidige dage vullen.
        getThisDate()

        //button take image
        binding.btnImage.setOnClickListener {
//            getThisImage()
            getImageFromGallary()
        }

        binding.btnsave.setOnClickListener {

        }
    }

    private fun getImageFromGallary() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = "image/*"
        startActivityForResult(pickIntent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            data?.data?.let { uri ->
                uploadFile(uri)
            }
    }

    private fun uploadFile(uri: Uri) {
       GlobalScope.launch {
            val stream = contentResolver.openInputStream(uri) ?: return@launch
            val request = RequestBody.create(MediaType.parse("image/*"), stream.readBytes()) // read all bytes using kotlin extension
            val filePart = MultipartBody.Part.createFormData(
                "file",
                "test.jpg",
                request
            )
                userApi.uploadFile(filePart)
            Log.d("MyActivity", "on finish upload file")
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

//    private fun getThisImage() {
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(android.Manifest.permission.CAMERA),
//                101
//            )
//            return
//        } else {
//            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(i, 101)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        var image = data?.getParcelableExtra<Bitmap>("data")
//        binding.test.setImageBitmap(image)
//        timer()
//    }

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


//private fun ContentResolver.getFileName(imageUri: Uri): String {
//    var name = ""
//    var returnCursor = this.query(imageUri, null,null,null,null)
//    if(returnCursor!=null){
//        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//        returnCursor.moveToFirst()
//        name = returnCursor.getString(nameIndex)
//        returnCursor.close()
//    }
//    return name
//}
