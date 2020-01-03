package com.flood_android.ui.feed.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flood_android.R
import com.flood_android.ui.feed.FeedDetailActivity
import com.flood_android.ui.feed.data.FeedDetailRecommentData
import com.flood_android.ui.feed.data.SubCommentData
import com.flood_android.util.GlobalData
import com.flood_android.util.OnSingleClickListener

class FeedDetailRecommentRVAdapter(
    private val ctx: Context,
    var dataList: ArrayList<SubCommentData>,
    private val commentIdx : String
) :
    RecyclerView.Adapter<FeedDetailRecommentRVAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed_detail_recomment, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int  = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        dataList[position].let{ item->
            Glide.with(ctx)
                .load(item.subcomment_user_img)
                .transform(CircleCrop(), CenterCrop())
                .into(holder.userImg)

            holder.userName.text = item.subcomment_user_name
            holder.time.text = item.time

            if(item.subcomment_content.indexOf("@")  == 0){
                val tag :String = item.subcomment_content.split(" ")[0]
                val content : String = item.subcomment_content.split(" ")[1]
                val subcomment : String = "<b>" + tag + "</b> " + content
                holder.contents.setText(Html.fromHtml(subcomment))
            }
            else{
                holder.contents.text = item.subcomment_content
            }

            // 답글에서 답글달기 클릭했을 때 처리하기
            holder.btnRecomment.setOnClickListener(object : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    (ctx as FeedDetailActivity).recomment(item.subcomment_user_name)
                    GlobalData.commentId = commentIdx
                }
            })
        }
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var userImg = itemView.findViewById(R.id.iv_rv_item_feed_detail_recomment_user_img) as ImageView
        var userName = itemView.findViewById(R.id.tv_rv_item_feed_detail_recomment_user_name) as TextView
        var time = itemView.findViewById(R.id.tv_rv_item_feed_detail_recomment_time) as TextView
        var contents = itemView.findViewById(R.id.tv_rv_item_feed_detail_recomment_contents) as TextView
        var btnRecomment = itemView.findViewById(R.id.btn_rv_item_feed_detail_recomment_recomment) as ConstraintLayout
    }
}