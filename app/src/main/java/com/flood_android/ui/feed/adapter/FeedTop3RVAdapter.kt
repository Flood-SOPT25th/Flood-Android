package com.flood_android.ui.feed.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flood_android.R
import com.flood_android.ui.feed.FeedDetailActivity
import com.flood_android.ui.feed.FeedFlipsSaveDialog
import com.flood_android.ui.feed.FeedFloodFragment
import com.flood_android.ui.feed.FeedFragment
import com.flood_android.ui.feed.data.FeedTop3Data
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.OnSingleClickListener

class FeedTop3RVAdapter(val ctx: Context, var dataList: ArrayList<FeedTop3Data>) :
    RecyclerView.Adapter<FeedTop3RVAdapter.Holder>() {

    var token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58"

    private val flipsSaveDialog by lazy{
        FeedFlipsSaveDialog()
    }

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
            Log.v("현주", item._id)

            holder.newsGradation.visibility = View.VISIBLE

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
                    intent.putExtra("feed_id", item._id)
                    ctx.startActivity(intent)
                }
            })

            Glide.with(ctx)
                .load(item.user_img)
                .centerCrop()
                .circleCrop()
                .into(holder.userImg)

            holder.userName.text = item.user_name
            holder.time.text = (ctx as MainActivity).calculateTime(item.time)
            holder.contents.text = item.contents

            // 피드 상세 페이지로 이동
            holder.userInfo.setOnClickListener(object : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    val intent = Intent(ctx, FeedDetailActivity::class.java)
                    intent.putExtra("feed_id", item._id)
                    ctx.startActivity(intent)
                }
            })

            holder.ivFlips.isSelected = item.flip_flag

            holder.btnFlips.setOnClickListener  (object : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    Log.v("현주", "눌려졌자나")
                    if (holder.ivFlips.isSelected) {     //북마크 취소
                        holder.ivFlips.isSelected = false
                        ctx.postBookmarkCancelRequest(token, item._id)
                }
                    else{   // 북마크하기
                        ctx.makeFlipDialog(holder.ivFlips)
                        //FeedFlipsSaveDialog().show(supoortFragmentManager, "")
                    }
                }
            })
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsTitle = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_title) as TextView
        var newsGradation = itemView.findViewById(R.id.view_rv_item_feed_flood_top3_gradation) as View
        var newsImg = itemView.findViewById(R.id.iv_rv_item_feed_flood_top3) as ImageView
        var contents = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_contents) as TextView
        var flipsNum = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_flips_cnt) as TextView
        var btnComments = itemView.findViewById(R.id.btn_rv_item_feed_flood_top3_comments) as ConstraintLayout
        var commentsNum = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_comments_cnt) as TextView
        var btnFlips = itemView.findViewById(R.id.btn_rv_item_feed_flood_top3_flips_flag) as ConstraintLayout
        var ivFlips = itemView.findViewById(R.id.iv_rv_item_feed_flood_top3_flips_flag) as ImageView
        var userImg = itemView.findViewById(R.id.iv_rv_item_feed_flood_top3_user) as ImageView
        var userName = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_user_name) as TextView
        var time = itemView.findViewById(R.id.tv_rv_item_feed_flood_top3_time) as TextView
        var userInfo = itemView.findViewById(R.id.cl_rv_item_feed_flood_top3_user_info) as ConstraintLayout
    }
}