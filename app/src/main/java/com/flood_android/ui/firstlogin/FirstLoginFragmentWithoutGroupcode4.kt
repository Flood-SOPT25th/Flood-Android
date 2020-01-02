package com.flood_android.ui.firstlogin


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_first_login_withgroupcode2.*

class FirstLoginFragmentWithoutGroupcode4 : Fragment() {
    private val GET_GALLERY_IMAGE = 200
    //private var imageview: ImageView? = null
    private var nameFlag = false
    private var rankFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_login_withgroupcode2, container, false)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_GALLERY_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri = data.data
            iv_first_login_withgroupcode2_editpart!!.setImageURI(selectedImageUri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtxt_first_login_withgroupcode2_profile_name.addTextChangedListener(nameWatcher)
        edtxt_first_login_withgroupcode2_profile_rank.addTextChangedListener(rankWatcher)

        iv_first_login_withgroupcode2_editpart.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            startActivityForResult(intent, GET_GALLERY_IMAGE)

        }
    }

    private val nameWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                nameFlag = true
                if (rankFlag)
                    toSignal(true)
                else
                    toSignal(false)
            } else {
                toSignal(false)
                nameFlag = false
            }
        }
    }

    private val rankWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if ((s ?: "").isNotEmpty()) {
                rankFlag = true
                if (nameFlag)
                    toSignal(true)
                else
                    toSignal(false)
            } else {
                toSignal(false)
                rankFlag = false
            }
        }
    }

    fun toSignal(flag: Boolean) {
        (activity as GroupCreationActivity).activateNextBtn(flag)
    }
}