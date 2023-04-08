package com.yasheep.weatherwear

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class baseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置状态栏
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){ window.insetsController?.hide(
            WindowInsets.Type.statusBars()) }
        else window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}