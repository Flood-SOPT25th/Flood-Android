package com.flood_android.ui.myprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flood_android.R
import kotlinx.android.synthetic.main.activity_my_profile_edit.*

class MyProfileEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_edit)

        // 서버에서 계정 정보 가져오기

        // 버튼
        setBtn()
    }

    private fun setBtn() {
        iv_myprofile_edit_back.setOnClickListener {
            finish()
        }
        tv_myprofile_edit_done.setOnClickListener {
            // 바뀐 계정 서버 통신
        }
        iv_myprofile_edit_password_set.setOnClickListener {
            val intent = Intent(this@MyProfileEditActivity, PasswordSetActivity::class.java)
            startActivity(intent)
        }
        iv_myprofile_edit_alarm_set.setOnClickListener {
            val intent = Intent(this@MyProfileEditActivity, AlarmSetActivity::class.java)
            startActivity(intent)
        }
        iv_myprofile_edit_invite_set.setOnClickListener {
            val intent = Intent(this@MyProfileEditActivity, InviteActivity::class.java)
            startActivity(intent)
        }
        iv_myprofile_edit_center.setOnClickListener {
            val intent = Intent(this@MyProfileEditActivity, CenterActivity::class.java)
            startActivity(intent)
        }
        iv_myprofile_edit_logout.setOnClickListener {
            LogoutDialog().show(supportFragmentManager, "Logout Dialog")
        }
    }
}
