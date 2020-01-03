package com.flood_android.ui.companydetail

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flood_android.R
import com.flood_android.util.OnSingleClickListener

class CompanyDetailCategoryRVAdapter(
    val ctx: Context,
    var dataList: List<String>,
    private val listener: (position: Int) -> Unit = {}
) : RecyclerView.Adapter<CompanyDetailCategoryRVAdapter.Holder>() {
    var selectedPosition: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size -1

    override fun onBindViewHolder(holder: Holder, position: Int) {

        dataList[position+1].let { item ->
            holder.categoryName.text = ("#").plus(item)
        }


        if (selectedPosition == position) {
            holder.categoryName.setTextColor(Color.WHITE)
            holder.categoryContainer.background = ctx.resources.getDrawable(R.drawable.rect_blue_21dp)
        } else {
            holder.categoryName.setTextColor(Color.parseColor("#d0d0d0"))
            holder.categoryContainer.background = ctx.resources.getDrawable(R.drawable.rect_white_21dp)
        }

        holder.itemView.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                selectedPosition = position
                Log.v("현주", selectedPosition.toString())
                notifyItemRangeChanged(0, itemCount)
                listener(position)
            }
        })
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName = itemView.findViewById(R.id.tv_feed_category) as TextView
        val categoryContainer = itemView.findViewById(R.id.cv_rv_item_feed_category ) as View

    }
}