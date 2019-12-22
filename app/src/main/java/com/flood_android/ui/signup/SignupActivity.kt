package com.flood_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flood_android.R

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen)
        setContentView(R.layout.activity_signup)
    }
}
