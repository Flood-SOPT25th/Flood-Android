package com.flood_android.ui.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_signup4.*


class SignupFragment_4 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup4, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()

        checkbox_signup4.setOnClickListener(View.OnClickListener {
            if (checkbox_signup4.isChecked) {
                toSignal(true)
            } else {
                //Perform action when you touch on checkbox and it change to unselected state
                toSignal(false)
            }
        })
    }

    fun toSignal(flag: Boolean) {
        (activity as SignupActivity).activateNextBtn(flag)
    }
}


