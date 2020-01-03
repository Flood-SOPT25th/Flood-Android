package com.flood_android.ui.myprofile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_my_profile_aler_dialog.*

class MyProfileAlerDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return inflater.inflate(R.layout.fragment_my_profile_aler_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_myprofile_edit_alert_dialog_ok.setOnClickListener {
            dismiss()
        }
    }

}
