package com.flood_android.ui.login


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.flood_android.R
import com.flood_android.util.GlobalData
import kotlinx.android.synthetic.main.fragment_login_alert_dialog.*

/**
 * A simple [Fragment] subclass.
 */
class LoginAlertDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return inflater.inflate(R.layout.fragment_login_alert_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_login_alert_dialog_message.text = GlobalData.loginDialogMessage
        tv_login_alert_dialog_ok.setOnClickListener {
            dismiss()
        }
    }


}
