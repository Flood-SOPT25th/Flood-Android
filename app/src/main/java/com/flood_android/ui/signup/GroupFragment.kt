package com.flood_android.ui.signup


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_group.*

class GroupFragment : Fragment() {
    lateinit var sendText : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        cstlay_signup_frag_group_3.setOnClickListener{

        }
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onStart() {
        super.onStart()
        edtxt_signup5_groupcode.addTextChangedListener(txtWatcher)
        sendText = edtxt_signup5_groupcode.text.toString()
        checkGroupCode(sendText)
    }

    fun toSignal(flag : Boolean){
        (activity as SignupActivity).activateNextBtn(flag)
    }

    fun checkGroupCode(sendText : String) {
        var flagServer = false

        //서버통신 필요 - 받은 코드를 서버에게 보내고 서버에서 판단해서 boolean 형태의 flag 준다고 생각
        // 여기서 btnFlag를 조정할 것 by toSignal
        toSignal(flagServer)
    }

    private val txtWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if((s?:"").isNotEmpty()){
                toSignal(true)
            }
        }
    }

    /*fun makeToast() {
        var inflater: LayoutInflater = layoutInflater
        val toastDesign = inflater.inflate(
            R.layout.toast_signup_group,
            (R.id.cstlay_signup_group) as ViewGroup
        )

        var toast = Toast(context)
        toast.setGravity(Gravity.CENTER, 0, 0)  //center를 기준으로 0 0 위치에 메시지 출력
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastDesign
        toast.show()
    }
*/

}
