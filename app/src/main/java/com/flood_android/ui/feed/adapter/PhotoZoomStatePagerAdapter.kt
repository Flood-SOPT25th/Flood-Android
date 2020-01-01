package com.flood_android.ui.feed.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.flood_android.R
import com.flood_android.ui.feed.PhotoZoomActivity

class PhotoZoomStatePagerAdapter (private val ctx : Context, private val list : ArrayList<String>) : PagerAdapter(){
    override fun isViewFromObject(view: View, p1: Any): Boolean {
        return view===p1 as View
    }

    override fun getCount(): Int = list.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view :View = LayoutInflater.from(container?.context).inflate(R.layout.vp_item_photo_zoom,container,false)
        val img_url: ImageView = view.findViewById(R.id.pv_rv_item_photo_zoom) as ImageView
//        var img_num: TextView = view.findViewById(R.id.tv_photo_zoom_cur_num) as TextView
//
//        img_num.text = position.toString()

        //(ctx as PhotoZoomActivity).changeNum(position)

        Glide.with(view)
            .load(list.get(position%list.size))
            .into(img_url)

        container?.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
}