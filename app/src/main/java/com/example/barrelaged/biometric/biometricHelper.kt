package com.example.barrelaged.biometric

import android.content.Context
import android.util.Base64
import androidx.biometric.BiometricManager
import com.example.barrelaged.api.apiCalls
import com.example.barrelaged.modals.BiomettricDto
import com.example.barrelaged.modals.user
import com.example.barrelaged.modals.userDto
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.spec.ECGenParameterSpec


object biometricHelper{
    private var keyStore: KeyStore? = null
    private lateinit var publicKey: PublicKey
    private lateinit var privateKey: PrivateKey

    fun hasBiometricOption(context: Context): Boolean {
        when (BiometricManager.from(context).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG
                or BiometricManager.Authenticators.BIOMETRIC_WEAK))
        {
            BiometricManager.BIOMETRIC_SUCCESS ->{
                return true
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                return false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                return false
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                return false
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                return false
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                return false
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                return false
            }
        }
        return false
    }

    suspend fun addFingerAuth(pass: String, email: String): user?{
        val keys = keyGenerator()
        val signature = sign(pass.toByteArray(Charsets.UTF_8), keys.private)

        val result = apiCalls().addFingerprint(BiomettricDto(
            signature = pass,
            signatureHash = Base64.encodeToString(signature, Base64.NO_WRAP),
            publicKey = Base64.encodeToString(keys.public.encoded, Base64.NO_WRAP),
            email = email
        ))

        if(result != null){
            val response = apiCalls().loginUser(userDto(
                Name = "String", Email = email, Password = pass, PublicKey = "String"
            ))
            if(response != null){
                return response
            }
            return null
        }

        return null
    }

    private fun sign(password: ByteArray, privateKey: PrivateKey): ByteArray{
        // Sign the password with the private key using SHA256 ECDSA
        val signature = Signature.getInstance("SHA256withECDSA")
        signature.initSign(privateKey)
        signature.update(password)
        return signature.sign()
    }

    private fun keyGenerator(): KeyPair{
        // Generate a key pair with ECDSA and a size of 256
        val keyPairGenerator = KeyPairGenerator.getInstance("EC")
        keyPairGenerator.initialize(ECGenParameterSpec("secp256r1"))
        return keyPairGenerator.generateKeyPair()
    }
}