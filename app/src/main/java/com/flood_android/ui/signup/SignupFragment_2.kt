package com.flood_android.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_signup2.*

class SignupFragment_2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup2, container, false)
        return view
    }

    var nameFlag = false
    var contactFlag = false

    fun toSignal(flag: Boolean) {
        (activity as SignupActivity).activateNextBtn(flag)
    }

    override fun onStart() {
        super.onStart()
        edtxt_signup2_name.addTextChangedListener(nameWatcher)
        edtxt_signup2_contact.addTextChangedListener(contactWatcher)
    }

    private val nameWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                nameFlag = true
                if (contactFlag) {
                    toSignal(true)
                    toAct2()
                } else
                    toSignal(false)
            } else {
                toSignal(false)
                nameFlag = false
            }
        }
    }

    private val contactWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                contactFlag = true
                if (nameFlag) {
                    toSignal(true)
                    toAct2()
                } else
                    toSignal(false)
            } else {
                toSignal(false)
                contactFlag = false
            }
        }
    }

    fun toAct2() {
        (activity as SignupActivity).signupInfo.name = edtxt_signup2_name.text.toString()
        (activity as SignupActivity).signupInfo.phone = edtxt_signup2_contact.text.toString()
    }
}