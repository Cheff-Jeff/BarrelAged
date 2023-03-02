package com.example.barrelaged

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.databinding.ActivityMainBinding
import com.example.barrelaged.modals.userDto
import com.example.barrelaged.validation.validationHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        emailFocusListener()
        passwordFocusListener()

        //btnSignUp click event
        binding.tvSignUp.setOnClickListener{
            startActivity(Intent(this@MainActivity, SignUp::class.java))
        }

        binding.btnGoogle.setOnClickListener{
            startActivity(Intent(this@MainActivity, BeerDetails::class.java))
        }

        //btnLogin click event
        binding.btnLogin.setOnClickListener {
            binding.tiEmail.error = validationHelper()
                .validateEmail(binding.tiEmail.editText?.text.toString())
            binding.tiPassword.error =validationHelper()
                .validateLoginPass(binding.tiPassword.editText?.text.toString())

            if(binding.tiEmail.error == null && binding.tiPassword.error == null)
            {
                binding.tvTitle.text = "Test"
                GlobalScope.launch {
                    val result = apiCalls().loginUser( userDto(
                        name = "",
                        email = binding.tiEmail.editText?.text.toString(),
                        password = binding.tiPassword.editText?.text.toString()
                    ))
                }
            }

            //            val title = findViewById<TextView>(R.id.tvTitle)
//            GlobalScope.launch(Dispatchers.Main) {
//                val users = api.getAllUsers()
//                if(users != null){
////                    Log.d("Users", users.toString())
//                    title.text = users[0].name
//                }
//                else{
//                    title.text = "Woeps"
////                    Log.d("error", "Oeps")
//                }
//            }
        }
    }

    private fun emailFocusListener(){
        binding.tiEmail.editText?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                binding.tiEmail.error = validationHelper().validateEmail(
                    binding.tiEmail.editText?.text.toString()
                )
            }
        }
    }

    private fun passwordFocusListener(){
        binding.tiPassword.editText?.setOnFocusChangeListener { _, focused ->
            if(!focused) {
                binding.tiPassword.error = validationHelper().validateLoginPass(
                    binding.tiPassword.editText?.text.toString()
                )
            }
        }
    }
}