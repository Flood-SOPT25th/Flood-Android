package com.flood_android.ui.post

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.flood_android.R
import okhttp3.MultipartBody
import java.sql.SQLException

class PostPostAdapter (private val ctx: Context, var data: ArrayList<PostPostImageData>, var imgsDataList: ArrayList<MultipartBody.Part>): RecyclerView.Adapter<PostPostAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostPostAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_tiem_uploadpost_picture, parent, false)

        return Holder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var options: RequestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(10))

        data[position].let{ item->
            Glide.with(ctx)
                .load(item.images)
                .apply(options)
                .into(holder.pictureIv)

            holder.closeIv.setOnClickListener{
                try{
                    imgsDataList.removeAt(position);
                    //(ctx as ReviewWriteActivity).deleteClipDataItem(item.idx)
                    data.removeAt(position);
                    this.notifyDataSetChanged();
                }
                catch(e: SQLException){
                    e.printStackTrace()
                }
            }
        }
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val pictureIv: ImageView = itemView.findViewById(R.id.iv_rv_item_uploadpost_picture)
        val closeIv: ImageView = itemView.findViewById(R.id.iv_rv_item_post_del)
    }

}