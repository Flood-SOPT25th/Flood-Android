package com.flood_android.ui.signup


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_signup4.*

class SignupFragment_4 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup4, container, false)
        checkbox_signup4.setOnClickListener {
            if (checkbox_signup4.isChecked) toSignal(true)
            else toSignal(false)
        }
        return view
    }

    fun toSignal(flag: Boolean) {
        (activity as SignupActivity).activateNextBtn(flag)
    }

}
