package com.example.barrelaged.biometric

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.biometric.BiometricManager
import java.math.BigInteger
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey


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

    fun addFingerAuth(pass: String){
        val keys = keyGenerator()
        publicKey = keys.public
        privateKey = keys.private

        val signature = sign(pass.toByteArray(Charsets.UTF_8), privateKey)

        // stuur naar server signature en public key
    }

    private fun sign(data: ByteArray, privateKey: PrivateKey): ByteArray{
        //Create signature from private key and byte array
        val signature = Signature.getInstance("SHA512withECDSA")
        signature.initSign(privateKey)
        signature.update(data)
        return signature.sign()
    }

    private fun verify(): Boolean{
        // ophalen user met public key
        //maak call met signature voor check
    }

//    fun verify(signature: ByteArray, data: ByteArray): Boolean{
//        //Create signature with public key
//        val verifySignature = Signature.getInstance("SHA512withECDSA")
//        verifySignature.initVerify(publicKey)
//        verifySignature.update(data)
//        return verifySignature.verify(signature)
//    }
//
//    fun verify(signature: ByteArray, data: ByteArray, publicKeyString: String): Boolean{
//        //verify with external public key
//        val verifySignature = Signature.getInstance("SHA512withECDSA")
//        val publicKey = KeyFactory.getInstance("EC")
//            .generatePublic( X509EncodedKeySpec(
//                android.util.Base64.decode(
//                    publicKeyString,
//                    android.util.Base64.DEFAULT
//                )
//            ))
//        verifySignature.initVerify(publicKey)
//        verifySignature.update(data)
//        return verifySignature.verify(signature)
//    }

//    fun cipherInit(){
//        val cipher = Cipher.getInstance(
//            KeyProperties.KEY_ALGORITHM_RSA + "/" +
//                    KeyProperties.BLOCK_MODE_CBC + "/" +
//                    KeyProperties.ENCRYPTION_PADDING_PKCS7)
//        keyStore?.load(null)
//        val key = keyStore?.getKey("KEY_ALIAS", null) as SecretKey
//        cipher?.init(Cipher.ENCRYPT_MODE, key)
////        return true
//    }

    private fun keyGenerator(): KeyPair{
//        keyStore = KeyStore.getInstance("AndroidKeyStore")
//        val keyPairGenerator: KeyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore")
//
//        keyStore?.load(null)
//        val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder("KEY_ALIAS",
//            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT).run {
//                setDigests(KeyProperties.DIGEST_SHA256)                         //Set of digests algorithms with which the key can be used
//                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)   //Set of padding schemes with which the key can be used when signing/verifying
//                setUserAuthenticationRequired(true)                             //Sets whether this key is authorized to be used only if the user has been authenticated, default false
//                build()
//            }
//        keyPairGenerator.initialize(parameterSpec)
//
//        return keyPairGenerator.genKeyPair()

        //ECDSA (Elliptic Curve Digital Signature Algorithm)
        val keyPairGenerator = KeyPairGenerator.getInstance("EC")
        keyPairGenerator.initialize(256)
        return keyPairGenerator.genKeyPair()
    }
}