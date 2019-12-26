package com.flood_android.ui.feed.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flood_android.R
import com.flood_android.ui.feed.FeedDetailActivity
import com.flood_android.ui.feed.data.FeedTop3Data
import com.flood_android.util.OnSingleClickListener

class FeedTop3RVAdapter(val ctx: Context, var dataList: ArrayList<FeedTop3Data>) :
    RecyclerView.Adapter<FeedTop3RVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedTop3RVAdapter.Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed_flood_top3, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: FeedTop3RVAdapter.Holder, position: Int) {
        dataList[position].let{ item->
            Glide.with(ctx)
                .load(item.news_img)
                .centerCrop()
                .into(holder.newsImg)

            // 해당 뉴스 웹페이지로 이동
            holder.newsImg.setOnClickListener (object : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.news_url))
                    ctx.startActivity(intent)
                }
            })

            holder.newsTitle.text  = item.news_title
            holder.flipsNum.text = item.flips_num.toString()
            holder.commentsNum.text = item.comments_num.toString()

            // 피드 상세 페이지로 이동
            holder.btnComments.setOnClickListener(object: OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    val intent = Intent(ctx, FeedDetailActivity::class.java)
                    ctx.startActivity(intent)
                }
            })

            Glide.with(ctx)
                .load(item.user_img)
                .centerCrop()
                .circleCrop()
                .into(holder.userImg)

            holder.userName.text = item.user_name
            holder.time.text = item.time
            holder.contents.text = item.contents

            // 피드 상세 페이지로 이동
            holder.userInfo.setOnClickListener(object : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    val intent = Intent(ctx, FeedDetailActivity::class.java)
                    ctx.startActivity(intent)
                }
            })
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsTitle = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_title) as TextView
        var newsImg = itemView.findViewById(R.id.iv_rv_item_feed_flood_top3) as ImageView
        var flipsNum = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_flips_cnt) as TextView
        var btnComments = itemView.findViewById(R.id.btn_rv_item_feed_flood_top3_comments) as ConstraintLayout
        var commentsNum = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_flips_cnt) as TextView
        var userImg = itemView.findViewById(R.id.iv_rv_item_feed_flood_top3_user) as ImageView
        var userName = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_user_name) as TextView
        var time = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_time) as TextView
        var contents = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_contents) as TextView
        var userInfo = itemView.findViewById(R.id.cl_rv_item_feed_flood_top3_user_info) as ConstraintLayout
    }
}