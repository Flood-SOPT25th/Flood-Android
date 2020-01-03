package com.flood_android.ui.myprofile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.flood_android.R
import com.flood_android.ui.login.LoginActivity
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.GlobalData
import com.flood_android.util.SharedPreferenceController
import kotlinx.android.synthetic.main.fragment_login_alert_dialog.*
import kotlinx.android.synthetic.main.fragment_logout_dialog.*

class LogoutDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return inflater.inflate(R.layout.fragment_logout_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*tv_logout_dialog_ok.setOnClickListener {
            SharedPreferenceController.clearSPC(context!!)
            dismiss()
            // 로그아웃
            // 토큰 파괴 -> LoginActivity로
        }*/

        tv_logout_dialog_no.setOnClickListener {
            dismiss()
        }
    }


}
