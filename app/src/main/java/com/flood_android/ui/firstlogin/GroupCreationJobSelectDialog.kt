package com.flood_android.ui.firstlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import android.widget.NumberPicker
import com.flood_android.R
import kotlinx.android.synthetic.main.dialog_first_login_job_select.*
import kotlinx.android.synthetic.main.dialog_signup_question_select.*

class GroupCreationJobSelectDialog(
    val listener: (String) -> Unit = {}
) : RoundedBottomSheetDialogFragment() {

    private val list = arrayOf(
        "경영지원", "영업 및 지원",
        "연구 및 생산", "IT, 전산",
        "디자인",
        "기타"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_first_login_job_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val formatter = NumberPicker.Formatter { i ->
            i.toString() + " " + Character.toString(0x33A1.toChar())
        }

        numpick_first_login_dialog_job_select.apply {
            minValue = 0
            maxValue = list.size - 1
            displayedValues = list
            wrapSelectorWheel = false
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            setFormatter(formatter)
        }

        btn_first_login_dialog_job_select.setOnClickListener {
            listener(list[numpick_first_login_dialog_job_select.value])
            dismiss()
        }
    }
}