package com.flood_android.ui.company

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.flood_android.R

class CompanyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_detail)

        val code = intent.getStringExtra("code")
        Log.e("company detail",code.toString())
    }
}
