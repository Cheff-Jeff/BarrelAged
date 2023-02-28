package com.example.barrelaged.biometric

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager


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
}