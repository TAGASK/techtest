package com.example.techtest.utils

import android.app.Activity
import android.util.Log
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