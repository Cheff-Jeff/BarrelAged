package com.example.barrelaged.btnLoading

import android.content.Context
import com.google.android.material.R
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable

object btnLoading {
    fun startProcess(button: com.google.android.material.button.MaterialButton, context: Context){
        val processIndicator = CircularProgressIndicatorSpec(context, null, 0,
            R.style.Widget_Material3_CircularProgressIndicator_ExtraSmall)
        val icon = IndeterminateDrawable.createCircularDrawable(context, processIndicator)

        button.icon = icon
        button.text = null
    }

    fun endProcess(button: com.google.android.material.button.MaterialButton, txt: String) {
        button.icon = null
        button.text = txt
    }
}