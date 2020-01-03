package com.flood_android.ui.firstlogin


import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.os.bundleOf

import com.flood_android.R
import com.flood_android.util.GlobalData
import kotlinx.android.synthetic.main.fragment_first_login_without_groupcode3.*

class GroupCreationFragment3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_first_login_without_groupcode3,
            container,
            false
        )
    }

    /*
    Log.e("GCODE2",arguments?.getString("GCODE"))
    /*에러 안나길*/
    edtxt_first_login_withoutgroupcode3_copycode.text = arguments?.getString("GCODE")
    Log.v("dkfjkfjkd",arguments?.getString("GCODE"))
    Log.v("AHAHHAH",edtxt_first_login_withoutgroupcode3_copycode.text.toString())
     */

    override fun onResume() {
        super.onResume()

        //edtxt_first_login_withoutgroupcode3_copycode.text = (activity as GroupCreationActivity).groupcode
        edtxt_first_login_withoutgroupcode3_copycode.text = GlobalData.gCode
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
            view!!.findViewById(R.id.cstlay_groupcode_toast)
        )
        /*val toastDesign = inflater.inflate(
            R.layout.toast_groupcode_copy,
            view!!.findViewById(R.id.cstlay_groupcode_toast)
        )*/
        var toast = Toast(context)
        // toast.setGravity(Gravity.FILL_HORIZONTAL, 0, 110)  //center를 기준으로 0 0 위치에 메시지 출력
        toast.setGravity(Gravity.BOTTOM, 0, 70)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastDesign

        toast.show()
    }

}
