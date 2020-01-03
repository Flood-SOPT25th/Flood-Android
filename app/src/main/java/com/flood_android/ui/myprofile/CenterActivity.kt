package com.flood_android.ui.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flood_android.R
import kotlinx.android.synthetic.main.activity_center.*

class CenterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_center)

        iv_center_back.setOnClickListener {
            finish()
        }
        iv_center_done.setOnClickListener {
            finish()
        }
    }
}
