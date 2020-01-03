package com.flood_android.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.flood_android.R
import com.flood_android.ui.login.LoginActivity
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.GifDrawableImageViewTarget
import com.flood_android.util.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen)
        setContentView(R.layout.activity_splash)

        Glide.with(this).load(R.drawable.and_splash).into(GifDrawableImageViewTarget(splash_image,1))

        val handler = Handler()
        handler.postDelayed(Runnable {
            if(SharedPreferenceController.getAuthorization(this@SplashActivity) == ""){
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("splash", 1)
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 4000)


    }

}