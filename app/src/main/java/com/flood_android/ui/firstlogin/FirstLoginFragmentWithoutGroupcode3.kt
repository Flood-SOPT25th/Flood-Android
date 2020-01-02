package com.flood_android.ui.firstlogin


import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast

import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_first_login_without_groupcode3.*

class FirstLoginFragmentWithoutGroupcode3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(
            R.layout.fragment_first_login_without_groupcode3,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgbtn_first_login_withoutgroupcode3_copycode.setOnClickListener{
            setClipBoardGroupcode(requireContext(),edtxt_first_login_withoutgroupcode3_copycode.text.toString())
        }
    }

    private fun setClipBoardGroupcode(context: Context, groupcode:String){
        val clipboardManager : ClipboardManager = context.getSystemService(Activity.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label",groupcode)
        clipboardManager.setPrimaryClip(clipData)
        makeToast()
    }

    fun makeToast() {
        var inflater: LayoutInflater = layoutInflater
        val toastDesign = inflater.inflate(
            R.layout.toast_groupcode_copy,
            view!!.findViewById(R.id.cl_feed_save_flips_categpty)
        )
        var toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 120)  //center를 기준으로 0 0 위치에 메시지 출력
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastDesign

        toast.show()
    }
}
