package com.flood_android.ui.feed.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.flood_android.R
import com.flood_android.ui.feed.FeedCategoryFragment
import com.flood_android.ui.feed.FeedFloodFragment
import com.flood_android.ui.feed.data.FeedCategoryData
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.OnSingleClickListener
import java.lang.Exception

class FeedCategoryRVAdapter(
    private val ctx: Context, var dataList: ArrayList<String> = arrayListOf(),  private val listener: (position: Int) -> Unit = {}
) : RecyclerView.Adapter<FeedCategoryRVAdapter.Holder>() {
    var selectedPosition: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(position)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName = itemView.findViewById(R.id.tv_feed_category) as TextView
        val categoryContainer = itemView.findViewById(R.id.cv_rv_item_feed_category ) as View

        fun onBind(position: Int) {
            if (position == 0) {
                categoryName.text = "Flood"
            } else {
                dataList[position].let { item ->
                    categoryName.text = ("#").plus(item)
                }
            }

            if (selectedPosition == position){
                categoryName.setTextColor(Color.WHITE)
                categoryContainer.background = ctx.resources.getDrawable(R.drawable.rect_blue_21dp)
                //holder.categoryName.typeface = ctx.resources.getFont(R.font.notosanscjkkrmedium)
            }
            else{
                categoryName.setTextColor(ctx.resources.getColor(R.color.colorMainBlue))
                categoryContainer.background = ctx.resources.getDrawable(R.drawable.rect_white_21dp)
                //holder.category_name.typeface = ctx.resources.getFont(R.font.notosansbold)
            }

            itemView.setOnClickListener(object : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    selectedPosition = position
                    Log.v("현주", selectedPosition.toString())
                    notifyItemRangeChanged(0, itemCount)
                    listener(position)
                }
            })
        }
    }
}