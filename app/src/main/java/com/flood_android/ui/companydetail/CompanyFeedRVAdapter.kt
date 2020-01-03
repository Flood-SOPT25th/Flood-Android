package com.flood_android.ui.companydetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.flood_android.R
import com.flood_android.ui.companydetail.CompanyDetailActivity
import com.flood_android.ui.feed.FeedDetailActivity
import com.flood_android.ui.feed.FeedFlipsSaveDialog
import com.flood_android.ui.feed.FeedFragment
import com.flood_android.ui.feed.WebViewActivity
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController

class CompanyFeedRVAdapter(private val ctx: Context, var dataList: ArrayList<FeedData>) :
    RecyclerView.Adapter<CompanyFeedRVAdapter.Holder>() {

    var token: String = SharedPreferenceController.getAuthorization(ctx)!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyFeedRVAdapter.Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed_flood_today, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        dataList[position].let { item ->
            Glide.with(ctx)
                .load(item.user_img)
                .circleCrop()
                .centerCrop()
                .into(holder.userImg)

            holder.userName.text = item.user_name
            holder.category.text = item.category
            holder.time.text = (ctx as CompanyDetailActivity).calculateTime(item.time)

            if (item.contents == "") {
                setGone(holder.contents)
            } else {
                holder.contents.text = item.contents
            }

            // 사진이 있을 때 사진 나타내기
            var pic_num: Int? = item.pic_list.size
            when (pic_num!!) {
                0 -> setGone(holder.cvImage)
                1 -> {
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
                else -> {
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


                    var etc_num = pic_num - 3
                    holder.pic_etc_num.text = "+${etc_num}"
                    setVisible(holder.pic_etc)
                    setVisible(holder.pic_etc_num)

                }
            }

            if (item.news_url == "")
                setGone(holder.container_news)
            else{
                holder.container_news.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(v: View) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.news_url))
                        ctx.startActivity(intent)
                    }
                })
            }
            holder.clFeed.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    val intent = Intent(ctx, FeedDetailActivity::class.java)
                    item._id.let {
                        intent.putExtra("feed_id", item._id)
                        ctx.startActivity(intent)
                    }
                }
            })

            holder.news_title.text = item.news_title

            if (item.news_img == ""){
                setGone(holder.news_img)
            }else{
                Glide.with(ctx)
                    .load(item.news_img)
                    .transform(CenterCrop(), RoundedCorners(21))
                    .into(holder.news_img)
            }

            if (item.news_contents == ""){
                setGone(holder.news_contents)
            }else{
                holder.news_contents.text = item.news_contents
            }

            holder.flips_num.text = item.flips_num.toString()
            holder.comments_num.text = item.comments_num.toString()

            holder.ivFlips.isSelected = item.bookmark_flag

            holder.btnFlips.visibility = View.INVISIBLE

            /*holder.btnFlips.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (holder.ivFlips.isSelected)  //북마크 취소
                    {
                        holder.ivFlips.isSelected = false
                        (ctx).postBookmarkCancelRequest(token, item._id)
                    } else {   // 북마크하기
                        //ctx.makeFlipDialog(holder.ivFlips)
                        flipsSaveDialog.show(ctx.supportFragmentManager, "")
                    }
                }
            })*/
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var clFeed = itemView.findViewById(R.id.cl_rv_item_feed) as ConstraintLayout

        var category = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_category) as TextView
        var userName = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_user_name) as TextView
        var userImg = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_user_img) as ImageView
        var time = itemView.findViewById(R.id.tv_rv_item_feed_flood_today_time) as TextView
        var contents =
            itemView.findViewById(R.id.tv_rv_item_feed_flood_today_user_contents) as TextView

        ///////////////////사진이 있을 때 없을 때, 뉴스가 있을 때 없을 때 구분하기
        var cvImage = itemView.findViewById(R.id.cv_rv_item_feed_flood_today) as CardView

        var pic1 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_1) as ImageView

        var container_pic2 =
            itemView.findViewById(R.id.ll_rv_item_feed_flood_today_pic_2) as LinearLayout
        var pic2_1 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_2_1) as ImageView
        var pic2_2 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_2_2) as ImageView

        var container_pic3 =
            itemView.findViewById(R.id.cl_rv_item_feed_flood_today_pic_3) as ConstraintLayout
        var pic3_1 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_3_1) as ImageView
        var pic3_2 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_3_2) as ImageView
        var pic3_3 = itemView.findViewById(R.id.iv_rv_item_feed_flood_today_pic_3_3) as ImageView

        var pic_etc = itemView.findViewById(R.id.cl_rv_item_feed_flood_today_pic_3_3_black) as ConstraintLayout
        var pic_etc_num = itemView.findViewById(R.id.tv_rv_item_feed_flood_todat_pic_3_3) as TextView

        var container_news =
            itemView.findViewById(R.id.cl_rv_item_feed_flood_today_news) as ConstraintLayout

        var news_title =
            itemView.findViewById(R.id.tv_rv_item_feed_flood_today_news_title) as TextView
        var news_contents =
            itemView.findViewById(R.id.tv_rv_item_feed_flood_today_news_contents) as TextView
        var news_img =
            itemView.findViewById(R.id.iv_tv_rv_item_feed_flood_today_news_img) as ImageView

        var flips_num =
            itemView.findViewById(R.id.tv_rv_item_feed_flood_today_flips_cnt) as TextView
        var comments_num =
            itemView.findViewById(R.id.tv_rv_item_feed_flood_today_comments_cnt) as TextView

        // flips flag 있으면 넣기
        var btnFlips =
            itemView.findViewById(R.id.btn_rv_item_feed_flood_today_flips_flag) as ConstraintLayout
        var ivFlips =
            itemView.findViewById(R.id.iv_rv_item_feed_flood_today_flips_flag) as ImageView
    }

    private fun setGone(view: View) {
        view.visibility = View.GONE
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun setInvisible(view: View) {
        view.visibility = View.INVISIBLE
    }
}