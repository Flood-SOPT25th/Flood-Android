package com.flood_android.ui.post


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.flood_android.R
import kotlinx.android.synthetic.main.fragment_post_category_dialog.*
import java.io.Serializable

class PostCategoryDialog : DialogFragment() {

    lateinit var orgCategory: Serializable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_category_dialog, container, false)

        Log.e("청하", orgCategory.toString())
        var orgCategory = arguments!!.getSerializable("categoryList")

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var orgCategory = arguments!!.getSerializable("categoryList")
        setView(orgCategory)

        btn_category_done.setOnClickListener {
            (context as PostActivity).setCategory(tv_category_dialog.text)
            dismiss()
        }
    }

    private fun setView(orgCategory: Serializable?) {
        tv_category_dialog.text = orgCategory.toString()
    }


}
