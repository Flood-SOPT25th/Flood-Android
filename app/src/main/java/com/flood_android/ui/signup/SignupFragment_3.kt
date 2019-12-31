package com.flood_android.ui.signup

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager

import com.flood_android.R
import com.orhanobut.dialogplus.DialogPlus
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.dialog_signup_question_select.*
import kotlinx.android.synthetic.main.fragment_signup2.*
import kotlinx.android.synthetic.main.fragment_signup3.*
import kotlinx.android.synthetic.main.fragment_signup4.*

class SignupFragment_3 : Fragment() {

    var questionFlag = false
    var answerFlag = false

    private val qListener = View.OnClickListener { questionDialog.dismiss() }
     private val questionDialog: SignupQuestionDialog by lazy {
         SignupQuestionDialog(requireContext(), qListener) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup3, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        // dialog에 listener 달아서 결과만 반환, post activity 참고할 것
        edtxt_signup3_question.setOnClickListener(View.OnClickListener {
            SignupQuestionDialog(requireContext(),View.OnClickListener {  }).btn_signup_dialog_question.setTextColor(Color.parseColor("#d1d1d1"))
            SignupQuestionDialog(requireContext(),View.OnClickListener {  }).show(getFragmentManager()!!, "fragment_dialog_test")
        })
        edtxt_signup3_question.addTextChangedListener(questionWatcher)
        edtxt_signup3_answer.addTextChangedListener(answerWatcher)
    }

    fun toSignal(flag: Boolean) {
        (activity as SignupActivity).activateNextBtn(flag)
    }

    private val questionWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                questionFlag = true
                if (answerFlag)
                    toSignal(true)
                else
                    toSignal(false)
            } else {
                toSignal(false)
                questionFlag = false
            }
        }
    }
    private val answerWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                answerFlag = true
                if (questionFlag)
                    toSignal(true)
                else
                    toSignal(false)
            } else {
                toSignal(false)
                answerFlag = false
            }
        }
    }
}




