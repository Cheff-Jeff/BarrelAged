package com.example.barrelaged.validation

import android.util.Patterns

class validationHelper {
    fun validateEmail(input: String): String? {
        if(input != ""){
            if(Patterns.EMAIL_ADDRESS.matcher(input).matches()){
                return null
            }
        }
        return "Invalid email address."
    }

    fun validateLoginPass(input: String): String?{
        if(input != ""){
            if(input.length > 1) {
                return null
            }
        }
        return "Invalid password."
    }

    fun validatePassword(input: String): String?{
        if(input != ""){
            if(input.length > 9) {
                return null
            }
        }
        return "Invalid password."
    }
    fun validateRePassword(input: String, repeatPass: String): String?{
        if(input != ""){
            if(input.length > 9) {
                if(input == repeatPass){
                    return null
                }
                return "password must match."
            }
        }
        return "Invalid password."
    }

    fun validateName(input: String): String?{
        if (input != ""){
            if(input.matches(Regex("^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ]+$"))){
                return null
            }
        }
        return "Invalid name."
    }
}