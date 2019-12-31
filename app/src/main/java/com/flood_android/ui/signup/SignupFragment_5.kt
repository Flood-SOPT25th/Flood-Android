package com.flood_android.ui.signup
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

import com.flood_android.R
import kotlinx.android.synthetic.main.activity_signup.*

class SignupFragment_5 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as SignupActivity).imgbtn_signup_x_black.isVisible=false
        (activity as SignupActivity).imgbtn_signup_left_arrow.isVisible=false
        (activity as SignupActivity).activateNextBtn(false)
        return inflater.inflate(R.layout.fragment_signup5, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as SignupActivity).activateNextBtn(true)
    }
}
