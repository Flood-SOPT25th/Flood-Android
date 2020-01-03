package com.flood_android.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_signup1.*
import java.util.regex.Pattern

class SignupFragment_1 : Fragment(){

    companion object {
        val EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }

    private fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(
            email
        ).matches()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup1,container,false)
        return view
    }

    var idFlag = false
    var pwdFlag = false
    var pwdCheckFlag = false


    override fun onStart() {
        super.onStart()
        edtxt_signup1_id.addTextChangedListener(idWatcher)
        edtxt_signup1_pw.addTextChangedListener(pwdWatcher)
        edtxt_signup1_pwconfirm.addTextChangedListener(pwdCheckWatcher)
    }

    fun toSignal(flag : Boolean){
        (activity as SignupActivity).activateNextBtn(flag)
    }

    private val idWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if((s?:"").isNotEmpty()){
                idFlag = true

                if(!checkEmail(edtxt_signup1_id.text.toString())) {
                    lv_signup_frag_1_1.visibility = View.GONE
                    tv_signup1_frag_emailformat.visibility = View.VISIBLE
                    lv_signup_frag_1_4_red.visibility = View.VISIBLE
                }
                else{
                    lv_signup_frag_1_1.visibility = View.VISIBLE
                    tv_signup1_frag_emailformat.visibility = View.GONE
                    lv_signup_frag_1_4_red.visibility = View.GONE
                }

                if(pwdFlag && pwdCheckFlag){
                    toSignal(true)
                    toAct1()
                }else{
                    toSignal(false)
                }
            }else{
                toSignal(false)
                idFlag = false
            }
        }
    }

    private val pwdWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if((s?:"").isNotEmpty()){
                pwdFlag = true
                if(idFlag && pwdCheckFlag){
                    toSignal(true)
                    toAct1()
                }else{
                    toSignal(false)
                }
            }else{
                toSignal(false)
                pwdFlag = false
            }
        }
    }

    private val pwdCheckWatcher = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if((s?:"").isNotEmpty() && ((s?:"").toString() == (edtxt_signup1_pw.text.toString()))){
                pwdCheckFlag = true

                if(pwdFlag && idFlag){
                    lv_signup_frag_1_3_red.visibility=View.GONE
                    tv_signup1_frag_nonmatch.visibility=View.GONE
                    toSignal(true)
                    toAct1()
                }else{toSignal(false) }
            }else{
                lv_signup_frag_1_3_red.visibility=View.VISIBLE
                tv_signup1_frag_nonmatch.visibility=View.VISIBLE
                toSignal(false)
                pwdCheckFlag = false
            }
        }
    }
    fun toAct1() {
        (activity as SignupActivity).signupInfo.email = edtxt_signup1_id.text.toString()
        (activity as SignupActivity).signupInfo.password = edtxt_signup1_pw.text.toString()
    }
}