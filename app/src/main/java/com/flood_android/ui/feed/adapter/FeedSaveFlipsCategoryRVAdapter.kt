package com.flood_android.ui.feed.adapter

import android.content.Context
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
import com.bumptech.glide.request.RequestOptions
import com.flood_android.R
import com.flood_android.ui.feed.FeedFragment
import com.flood_android.ui.feed.data.FeedSaveFlipsCategoryData
import com.flood_android.util.OnSingleClickListener
import kotlinx.android.synthetic.main.rv_item_feed_save_flips_category.view.*

class FeedSaveFlipsCategoryRVAdapter(
    private val ctx: Context, var dataList: ArrayList<FeedSaveFlipsCategoryData>
) : RecyclerView.Adapter<FeedSaveFlipsCategoryRVAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedSaveFlipsCategoryRVAdapter.Holder {
        val view: View = LayoutInflater.from(ctx)
            .inflate(R.layout.rv_item_feed_save_flips_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: FeedSaveFlipsCategoryRVAdapter.Holder, position: Int) {

        dataList[position].let { item ->
            Glide.with(ctx)
                .load(item.img)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(holder.categoryImg)
            holder.categoryName.text = item.category_name
            holder.categoryBtn.setOnClickListener {
                (object : OnSingleClickListener() {
                    override fun onSingleClick(v: View) {
                        (ctx as FeedFragment).dismissFlipDialog()   // dialog 끄기
                        (ctx as FeedFragment).makeToast(item.img, item.category_name)
                        /**
                         *  여기다가 북마크 추가했다는 서버 통신도 하긔
                         */
                    }
                })
            }
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryImg =
            itemView.findViewById(R.id.iv_rv_item_feed_save_flips_category) as ImageView
        var categoryName =
            itemView.findViewById(R.id.tv_rv_item_feed_save_flips_category) as TextView
        var categoryBtn =
            itemView.findViewById(R.id.cl_rv_item_feed_save_flips_category) as ConstraintLayout
    }
}