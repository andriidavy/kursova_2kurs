package com.example.registration.global

import android.content.Context
import android.widget.Toast

object ToastObj {
    fun longToastMake(message: String, context: Context?){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show()
    }
    fun shortToastMake(message: String, context: Context?){
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
    }
}