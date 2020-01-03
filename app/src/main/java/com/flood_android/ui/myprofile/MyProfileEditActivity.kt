package com.flood_android.ui.myprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.ui.main.MainActivity
import com.flood_android.ui.myprofile.get.User
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_my_profile_edit.*

class MyProfileEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile_edit)

        // 서버에서 계정 정보 가져오기
        getMyProfile()
        // 버튼
        setBtn()
    }

    private fun getMyProfile() {
        val getMyProfileEditResponse = ApplicationController.networkServiceUser
            .getMyProfileEditResponse(SharedPreferenceController.getAuthorization(this@MyProfileEditActivity)!!)
        getMyProfileEditResponse.safeEnqueue {
                setView(it.data.user, it.data.group.name)
        }
    }

    private fun setView(userData: User, groupName: String) {
        Glide.with(this)
            .load(userData.profileImage)
            .transform(CenterCrop(), CircleCrop())
            .into(iv_myprofile_edit_image)

        tv_myprofile_edit_name.text = userData.name
        tv_myprofile_edit_position.text = userData.rank
        tv_myprofile_edit_department.text = groupName
        tv_myprofile_edit_email.text = userData.email
        tv_myprofile_edit_tel.text = userData.phone

        if (userData.admin){
            txt_org_set.visibility = View.VISIBLE
            iv_myprofile_edit_org_set.visibility = View.VISIBLE
        }
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
