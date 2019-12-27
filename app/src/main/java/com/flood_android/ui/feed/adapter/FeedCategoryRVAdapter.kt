package com.flood_android.ui.feed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flood_android.R
import com.flood_android.ui.feed.data.FeedCategoryData

class FeedCategoryRVAdapter(
    private val ctx: Context, var dataList: ArrayList<String>
) : RecyclerView.Adapter<FeedCategoryRVAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedCategoryRVAdapter.Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: FeedCategoryRVAdapter.Holder, position: Int) {
        if (position == 0) {
            holder.categoryName.text = "Flood"
        } else {
            dataList[position].let { item ->
                holder.categoryName.text = ("#").plus(item)

            }
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryName = itemView.findViewById(R.id.tv_feed_category) as TextView
    }
}