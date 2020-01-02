package com.flood_android.ui.signup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.flood_android.R
import kotlinx.android.synthetic.main.dialog_signup_question_select.*


class SignupQuestionDialog(
    val listener: (String) -> Unit = {}
) : RoundedBottomSheetDialogFragment()  {

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
        return inflater.inflate(R.layout.dialog_signup_question_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val formatter = NumberPicker.Formatter { i -> i.toString() + " " + Character.toString(0x33A1.toChar())
        }

        numpick_signup_question_dialog.apply {
            minValue = 0
            maxValue = list.size - 1
            displayedValues = list
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            setFormatter(formatter)
        }

        btn_signup_dialog_question.setOnClickListener {
            listener(list[numpick_signup_question_dialog.value])
            dismiss()
        }
    }
}
