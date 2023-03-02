package com.example.barrelaged.biometric

import android.content.Context
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.biometric.BiometricManager
import java.security.KeyPairGenerator


object biometricHelper{
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

    fun keyGenerator(){
        KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore")
        KeyPairGenerator.initialize(new KeyGenParameterSpec.Builder(KEY_NAME,
            KeyProperties.PURPOSE_SIGN)
            .setDigests(KeyProperties.DIGEST_SHA256)
            .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
            .setUserAuthenticationRequired(true)
            .build()
        keyPairGenerator.generateKeyPair();
    }
}