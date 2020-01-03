package com.flood_android.ui.mypage.adapter

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.flood_android.R
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.ui.myflip.MyFlipDetailActivity
import com.flood_android.util.OnSingleClickListener

class MypageMyFlipRVAdapter(private val ctx: Context, var dataList: ArrayList<BookmarkData>) :
    RecyclerView.Adapter<MypageMyFlipRVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_mypage_myflip, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int  = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        dataList[position].let{item ->
            Glide.with(ctx)
                .load(item.thumb)
                .transform(CenterCrop(), RoundedCorners(40))
                .into(holder.ivThumb)
            holder.flipCategory.text = item.categoryName
            holder.flipCnt.text = "${item.count} flips"

            holder.clCategory.setOnClickListener(object : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    val intent = Intent(ctx, MyFlipDetailActivity::class.java)
//                    intent.putExtra("i", item)
                    ctx.startActivity(intent)
                }
            })
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivThumb = itemView.findViewById(R.id.iv_rv_item_mypage_myflip) as ImageView
        val flipCnt = itemView.findViewById(R.id.tv_rv_item_mypage_flipnum) as TextView
        val flipCategory = itemView.findViewById(R.id.tv_rv_item_mypage_myflip_category) as TextView
        val clCategory = itemView.findViewById(R.id.cl_rv_item_mypage_myflip) as ConstraintLayout
    }
}