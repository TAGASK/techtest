package com.example.techtest.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.techtest.BuildConfig

fun Activity.logD(message: String) {
    if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
}

class AppLog {
    companion object {
        fun logD(message: String) {
            if (BuildConfig.DEBUG) Log.d(this::class.java.simpleName, message)
        }
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}