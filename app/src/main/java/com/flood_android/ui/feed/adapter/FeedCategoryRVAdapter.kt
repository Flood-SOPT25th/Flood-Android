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
    private val ctx: Context, var dataList: ArrayList<String>
) : RecyclerView.Adapter<FeedCategoryRVAdapter.Holder>() {
    var selectedPosition: Int = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedCategoryRVAdapter.Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_feed_category, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FeedCategoryRVAdapter.Holder, position: Int) {

        if (position == 0) {
            holder.categoryName.text = "Flood"
        } else {
            dataList[position].let { item ->
                holder.categoryName.text = ("#").plus(item)
            }
        }

        if (selectedPosition == position){
            holder.categoryName.setTextColor(Color.WHITE)
            holder.categoryContainer.background = ctx.resources.getDrawable(R.drawable.rect_blue_21dp)
            //holder.categoryName.typeface = ctx.resources.getFont(R.font.notosanscjkkrmedium)
        }
        else{
            holder.categoryName.setTextColor(ctx.resources.getColor(R.color.colorMainBlue))
            holder.categoryContainer.background = ctx.resources.getDrawable(R.drawable.rect_white_21dp)
            //holder.category_name.typeface = ctx.resources.getFont(R.font.notosansbold)
        }

        // 카테고리를 클릭했을 때
       holder.categoryContainer.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                try{
                    selectedPosition = position
                    Log.v("현주", selectedPosition.toString())
                    notifyItemRangeChanged(0, itemCount)

                    val transaction: FragmentTransaction = (ctx as MainActivity).supportFragmentManager.beginTransaction()
                    if (selectedPosition == 0){
                        transaction.replace(R.id.fl_feed_fragment_frag, FeedFloodFragment())
                        transaction.addToBackStack(null)
                    }else{
                        transaction.replace(R.id.fl_feed_fragment_frag, FeedCategoryFragment(dataList[position]))
                    }
                    transaction.commit()
                }catch(e : Exception){
                }
            }
        })
    }
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryContainer = itemView.findViewById(R.id.cv_rv_item_feed_category) as ConstraintLayout
        var categoryName = itemView.findViewById(R.id.tv_feed_category) as TextView
    }
}