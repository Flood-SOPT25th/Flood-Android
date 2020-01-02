package com.flood_android.ui.firstlogin

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.flood_android.R
import kotlinx.android.synthetic.main.activity_first_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.dialog_first_login_withgroupcode_inconsistency.*

class GroupcodeMismatchDialog(context : Context, private val gokListener : View.OnClickListener?) : Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.55f
        window!!.attributes = lpWindow

        setContentView(R.layout.dialog_first_login_withgroupcode_inconsistency)

        if(gokListener != null){
            iv_dialog_first_login_groupcode.setOnClickListener(gokListener)
        }
    }

}