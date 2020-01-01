package com.flood_android.ui.mypage.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flood_android.R
import com.flood_android.ui.mypage.data.MypageMyflipRvItem

class MyPageRVViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val iv_rv_item_mypage_myflip : ImageView = view.findViewById(R.id.iv_rv_item_mypage_myflip)
    private val tv_rv_item_mypage_myflip_category : TextView = view.findViewById(R.id.tv_rv_item_mypage_myflip_category)
    private val flipnum : TextView = view.findViewById(R.id.tv_rv_item_mypage_flipnum)

    fun bind(data : MypageMyflipRvItem){
        Glide.with(iv_rv_item_mypage_myflip.context).load(data.thumnail).into(iv_rv_item_mypage_myflip)
        tv_rv_item_mypage_myflip_category.text = data.category_name
        flipnum.setText(data.flipnum)
    }
}