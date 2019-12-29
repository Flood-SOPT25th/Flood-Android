package com.flood_android.ui.signup

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.widget.addTextChangedListener

import com.flood_android.R
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.dialog_signup_question_select.*
import kotlinx.android.synthetic.main.fragment_signup2.*
import kotlinx.android.synthetic.main.fragment_signup3.*

class SignupFragment_3 : Fragment() {

    private val questionOkDialog: SignupQuestionDialog by lazy {
        SignupQuestionDialog(this.requireContext(), qokListener)
    }
    private val qokListener = View.OnClickListener { questionOkDialog.dismiss() }

    private val list = arrayOf(
        "아버지의 성함은?", "어머니의 성함은?",
        "기억에 남는 추억의 장소는?", "자신의 인생 좌우명은?",
        "가장 기억에 남는 선생님 성함은?",
        "받았던 선물 중 가장 기억에 남는 선물은?",
        "유년시절 가장 생각나는 친구 이름은?",
        "인상깊게 읽은 책 이름은?", "자신이 첫 번째로 존경하는 인물은?",
        "내가 좋아하는 캐릭터는?"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup3, container, false)
        numpick_signup_question_dialog.apply {
            minValue = 0
            maxValue = list.size - 1
            displayedValues = list
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
        return view
    }

    override fun onStart() {
        super.onStart()
       /* edtxt_signup3_question.addTextChangedListener(questionWatcher)
        edtxt_signup3_answer.addTextChangedListener(answerWatcher)*/
        edtxt_signup3_question.setOnClickListener{questionOkDialog.show()}
        /*edtxt_signup3_question.setText(numpick_signup_question_dialog.value)*/
    }

    fun toSignal(flag: Boolean) {
        (activity as SignupActivity).activateNextBtn(flag)
    }

    var questionFlag = false
    var answerFlag = false

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




