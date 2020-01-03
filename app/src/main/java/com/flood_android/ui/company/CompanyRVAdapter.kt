package com.flood_android.ui.company

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.flood_android.R
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.OnSingleClickListener

class CompanyRVAdapter(
    val ctx: Context,
    var dataList: List<GroupArr>
) : RecyclerView.Adapter<CompanyRVAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_company, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var options: RequestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(50))

        Log.v("postygyg", "postygyg")
        Glide.with(ctx)
            .load(dataList[position].groupImage)
            .apply(options)
            .into(holder.companyImage)

        Glide.with(ctx)
            .load(dataList[position].groupIcon)
            .transform(CenterCrop(), CircleCrop())
            .apply(options)
            .into(holder.companyLogo)

        holder.companyName.text = dataList[position].name
        Log.v("청하청하", dataList[position].name)

        if (dataList[position].category.size == 2){
            holder.category1.text = dataList[position].category[1].trim()
            holder.category2.visibility = View.GONE
            holder.category3.visibility = View.GONE
        }
        else if (dataList[position].category.size == 3){
            holder.category1.text = dataList[position].category[1].trim()
            holder.category2.text = dataList[position].category[2].trim()
            holder.category3.visibility = View.GONE
        }
        else if (dataList[position].category.size > 3){
            holder.category1.text = dataList[position].category[1].trim()
            holder.category2.text = dataList[position].category[2].trim()
            holder.category3.text = dataList[position].category[3].trim()
        }

        holder.companyImage.setOnClickListener {
            (ctx as MainActivity).detailset(dataList[position].groupCode)
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val companyLogo: ImageView = itemView.findViewById(R.id.iv_rv_item_company_logo)
        val companyImage: ImageView = itemView.findViewById(R.id.iv_rv_item_company_img)
        val companyName: TextView = itemView.findViewById(R.id.tv_rv_item_company_name)
        val category1: TextView = itemView.findViewById(R.id.tv_rv_item_company_category1)
        val category2: TextView = itemView.findViewById(R.id.tv_rv_item_company_category2)
        val category3: TextView = itemView.findViewById(R.id.tv_rv_item_company_category3)

    }

}