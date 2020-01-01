package com.flood_android.ui.bookmarkedit


import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment

import com.flood_android.R
import com.flood_android.util.GlobalData
import kotlinx.android.synthetic.main.dialog_bookmark_edit_name.*


class BookmarkNewFlipDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        dialog!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return inflater.inflate(R.layout.dialog_bookmark_edit_name, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_bookmark_edit_name_dialog_cancel.setOnClickListener {
            dismiss()
        }
        btn_bookmark_edit_name_dialog_ok.setOnClickListener {
            // 이름 값을 넘겨줌.

            val edtBookmarkName = edt_bookmark_edit_name_dialog.text.toString()
            (context as BookmarkEditActivity).addItem(edtBookmarkName)
            GlobalData.addFlip.add(edtBookmarkName)
            dismiss()

            Log.e("flipadd", GlobalData.addFlip.toString())
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        edt_bookmark_edit_name_dialog.setText("")
    }

    fun setEditFolderName(foldername:String){
        edt_bookmark_edit_name_dialog.setText(foldername)
    }

}
