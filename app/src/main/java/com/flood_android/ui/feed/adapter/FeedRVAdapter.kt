package com.flood_android.ui.feed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flood_android.R
import com.flood_android.ui.feed.data.FeedData

class FeedRVAdapter (private val ctx : Context, var dataList: ArrayList<FeedData>): RecyclerView.Adapter<FeedRVAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedRVAdapter.Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed_flood_today, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int  = dataList.size

    override fun onBindViewHolder(holder: FeedRVAdapter.Holder, position: Int) {
        dataList[position].let{item->
            Glide.with(ctx)
                .load(item.user_img)
                .circleCrop()
                .centerCrop()
                .into(holder.userImg)

            holder.userName
            holder.category.text = item.category
            holder.time.text = item.time
            holder.contents.text = item.contents

            // 사진이 있을 때 사진 나타내기
            var pic_num : Int?= item.pic_list.size
            when(pic_num!!){
                1 ->{
                    setVisible(holder.pic1)
                    setInvisible(holder.container_pic2)
                    setInvisible(holder.container_pic3)

                    Glide.with(ctx)
                        .load(item.pic_list[0])
                        .centerCrop()
                        .into(holder.pic1)
                }
                2 -> {
                    setInvisible(holder.pic1)
                    setVisible(holder.container_pic2)
                    setInvisible(holder.container_pic3)

                    Glide.with(ctx)
                        .load(item.pic_list[0])
                        .centerCrop()
                        .into(holder.pic2_1)
                    Glide.with(ctx)
                        .load(item.pic_list[1])
                        .centerCrop()
                        .into(holder.pic2_2)
                }
                3 -> {
                    setInvisible(holder.pic1)
                    setInvisible(holder.container_pic2)
                    setVisible(holder.container_pic3)

                    Glide.with(ctx)
                        .load(item.pic_list[0])
                        .centerCrop()
                        .into(holder.pic3_1)
                    Glide.with(ctx)
                        .load(item.pic_list[1])
                        .centerCrop()
                        .into(holder.pic3_2)
                    Glide.with(ctx)
                        .load(item.pic_list[2])
                        .centerCrop()
                        .into(holder.pic3_3)
                }
                //4개 이상
                else ->{
                    //더하기 버튼이랑 까맣게 만들기
                    return
                }
            }

            holder.news_title.text = item.news_title
            holder.news_contents.text = item.news_contents

            holder.flips_num.text = item.flips_num.toString()
            holder.comments_num.text = item.comments_num.toString()
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var category = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_category) as TextView
        var userName = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_user_name) as TextView
        var userImg = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_user_img) as ImageView
        var time = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_time) as TextView
        var contents = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_user_contents) as TextView

///////////////////////////////
        ///////////////////사진이 있을 때 없을 때, 뉴스가 있을 때 없을 때 구분하기

        var pic1 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_1) as ImageView

        var container_pic2 = itemView.findViewById(R.id.ll_rv_item_feed_flood_today_pic_2) as LinearLayout
        var pic2_1 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_2_1) as ImageView
        var pic2_2 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_2_2) as ImageView

        var container_pic3 = itemView.findViewById(R.id.cl_rv_item_feed_flood_today_pic_3) as ConstraintLayout
        var pic3_1 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_3_1) as ImageView
        var pic3_2 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_3_2) as ImageView
        var pic3_3 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_3_3) as ImageView

        var news_title = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_news_title) as TextView
        var news_contents = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_news_contents) as TextView

        var flips_num = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_flips_cnt) as TextView
        var comments_num = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_comments_cnt) as TextView

        // flips flag 있으면 넣기

    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun setInvisible(view: View) {
        view.visibility = View.INVISIBLE
    }
}