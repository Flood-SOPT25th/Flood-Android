package com.flood_android.ui.signup

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.flood_android.R
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_signup1.*

class SignupFragment_1 : Fragment(){
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
            // id가 비어있지 않고,
            if((s?:"").isNotEmpty()){
                idFlag = true

                if(pwdFlag && pwdCheckFlag){
                    toSignal(true)
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
            // id가 비어있지 않고,
            if((s?:"").isNotEmpty()){
                pwdFlag = true
                if(idFlag && pwdCheckFlag){
                    toSignal(true)
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
                Log.v("SignF", "체크1")
                if(pwdFlag && idFlag){
                    lv_signup_frag_1_3_red.visibility=View.GONE
                    tv_signup_frag_nonmatch.visibility=View.GONE
                    toSignal(true)
                }else{toSignal(false) }
            }else{
                lv_signup_frag_1_3_red.visibility=View.VISIBLE
                tv_signup_frag_nonmatch.visibility=View.VISIBLE
                toSignal(false)
                pwdCheckFlag = false
            }
        }
    }


    /*
    companion object{
        private var instance : SignupFragment_1? = null

        @Synchronized
        fun getInstance() : SignupFragment_1{
            if(instance == null){
                instance = SignupFragment_1()
            }
            return instance!!
        }
    }
    /
     */

}