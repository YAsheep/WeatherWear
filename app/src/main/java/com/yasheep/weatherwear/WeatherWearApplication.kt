package com.yasheep.weatherwear

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.yasheep.weatherwear.logic.network.WeatherWearNetwork
import kotlin.coroutines.suspendCoroutine

class WeatherWearApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        const val TOKEN = "JNIdi5lhrsYv5BI6"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}