package com.example.barrelaged

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.databinding.ActivitySignUpBinding
import com.example.barrelaged.modals.userDto
import com.example.barrelaged.validation.validationHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        nameFocusListener()
        emailFocusListener()
        passowrdFocusListener()
        rePasswordFocusListener()

        binding.tvLoginLink.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnSignUp.setOnClickListener {
            //validate
            binding.tiName.error = validationHelper().validateName(
                binding.tiName.editText?.text.toString()
            )
            binding.tiEmail.error = validationHelper().validateEmail(
                binding.tiEmail.editText?.text.toString()
            )
            binding.tiPassword.error = validationHelper().validatePassword(
                binding.tiPassword.editText?.text.toString(),
            )
            binding.tiRePassword.error = validationHelper().validateRePassword(
                binding.tiPassword.editText?.text.toString(),
                binding.tiRePassword.editText?.text.toString()
            )

            if(binding.tiName.error == null && binding.tiEmail.error == null &&
                binding.tiPassword.error == null && binding.tiRePassword.error == null)
            {
                GlobalScope.launch(Dispatchers.Main) {
                    val result = apiCalls().signUpCall( userDto(
                        name = binding.tiName.editText?.text.toString(),
                        email = binding.tiEmail.editText?.text.toString(),
                        password = binding.tiPassword.editText?.text.toString()
                    ))

                    if(result != null){
                        binding.tvTitle.text = "Done"
                    }
                }
            }
        }
    }

    private fun nameFocusListener(){
        binding.tiName.editText?.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.tiName.error = validationHelper().validateName(
                    binding.tiName.editText?.text.toString()
                )
            }
        }
    }

    private fun emailFocusListener(){
        binding.tiEmail.editText?.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.tiEmail.error = validationHelper().validateEmail(
                    binding.tiEmail.editText?.text.toString()
                )
            }
        }
    }

    private fun passowrdFocusListener(){
        binding.tiPassword.editText?.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.tiPassword.error = validationHelper().validatePassword(
                    binding.tiPassword.editText?.text.toString(),
                )
            }
        }
    }

    private fun rePasswordFocusListener(){
        binding.tiRePassword.editText?.setOnFocusChangeListener { _, focused ->
            if(!focused){
                binding.tiRePassword.error = validationHelper().validateRePassword(
                    binding.tiPassword.editText?.text.toString(),
                    binding.tiRePassword.editText?.text.toString()
                )
            }
        }
    }
}