package com.flood_android.ui.post

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flood_android.R
import com.flood_android.util.GlobalData.selectedCategory

class PostSelectCategoryDialogRVAdapter(var ctx: Context, var data: List<String>, var flag: List<Boolean>) :
    RecyclerView.Adapter<PostSelectCategoryDialogRVAdapter.Holder>() {

    interface ItemClick
    {
        fun onClick(view: View, position: Int)
    }
    var itemClick: ItemClick? = null

    var name: String? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_post_select_category_dialog_category, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = data.size

    fun changeflagData(newFlag: List<Boolean>) {
        flag = newFlag
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (position == 0){
            holder.categoryName.visibility = View.GONE
        }else{
            if(itemClick != null)
            {
                holder.itemView?.setOnClickListener { v ->
                    itemClick?.onClick(v, position)
                    selectedCategory = holder.categoryName.text.toString()
                }
            }
            holder.categoryName.text = data[position]

            if(flag[position]) {
                holder.categoryName.setTextColor(Color.parseColor("#282828"))
            }
            else {
                holder.categoryName.setTextColor(Color.parseColor("#d1d1d1"))
            }
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.tv_rv_item_post_select_category)

    }


}