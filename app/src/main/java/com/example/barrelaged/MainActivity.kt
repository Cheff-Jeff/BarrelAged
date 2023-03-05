package com.example.barrelaged

import android.content.Context
import android.content.Intent
import androidx.biometric.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.biometric.biometricHelper
import com.example.barrelaged.biometric.biometricHelper.addFingerAuth
import com.example.barrelaged.btnLoading.btnLoading
import com.example.barrelaged.databinding.ActivityMainBinding
import com.example.barrelaged.modals.userDto
import com.example.barrelaged.validation.validationHelper
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bioPrompt: BiometricPrompt
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(biometricHelper.hasBiometricOption(this)){
            activateBioPrompt()
        }

        emailFocusListener()
        passwordFocusListener()

        //btnSignUp click event
        binding.tvSignUp.setOnClickListener{
            startActivity(Intent(this@MainActivity, SignUp::class.java))
        }

        //btnLogin click event
        binding.btnLogin.setOnClickListener {

//            addFingerAuth(binding.tiPassword.editText?.text.toString())
            val btnText = binding.btnLogin.text.toString()
            btnLoading.startProcess(binding.btnLogin, this)

            binding.tiEmail.error = validationHelper()
                .validateEmail(binding.tiEmail.editText?.text.toString())
            binding.tiPassword.error =validationHelper()
                .validateLoginPass(binding.tiPassword.editText?.text.toString())

            if(binding.tiEmail.error == null && binding.tiPassword.error == null)
            {
                GlobalScope.launch(Dispatchers.Main) {
                    val result = apiCalls().loginUser( userDto(
                        Name = "String",
                        Email = binding.tiEmail.editText?.text.toString(),
                        Password = binding.tiPassword.editText?.text.toString(),
                        PublicKey = "String"
                    ))

                    if (result != null) {
                        val sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putInt("UserId", result.id)
                        //start activity

                    }
                    Toast.makeText(this@MainActivity, "Something went wrong.", Toast.LENGTH_SHORT).show()
                    btnLoading.endProcess(binding.btnLogin, btnText)
                }
            }
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

    private fun activateBioPrompt(){
        bioPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        bioPrompt.authenticate(BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint log in.")
            .setSubtitle("Log in using your finger print.")
            .setNegativeButtonText("Use e-mail and password.")
            .build())
    }
}