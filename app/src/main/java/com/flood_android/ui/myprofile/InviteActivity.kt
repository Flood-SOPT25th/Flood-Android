package com.flood_android.ui.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flood_android.R
import kotlinx.android.synthetic.main.activity_invite.*

class InviteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)

        iv_invite_back.setOnClickListener {
            finish()
        }
        tv_invite_invite.setOnClickListener {
            // 초대하기
        }
    }
}
