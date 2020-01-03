package com.flood_android.ui.bookmarkedit

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.flood_android.R
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.util.GlobalData


class BookmarkEditFolderRVAdapter(
    val ctx: Context,
    val dataList: ArrayList<BookmarkData>
) : RecyclerView.Adapter<BookmarkEditFolderRVAdapter.Holder>() {
    private var onItemClick: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(ctx).inflate(R.layout.rv_item_bookmark_edit_folder, parent, false)
        view.findViewById<ImageView>(R.id.iv_rv_item_bookmark_edit_folder_image)
            .setOnClickListener(onItemClick)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        var options: RequestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(30))
        Log.e("123", dataList[position].toString())
        Log.e("123", dataList.toString())

        // 첫번째 버튼
        if (position == 0) {
            dataList[position].let { item ->
                holder.add.visibility = View.VISIBLE
                holder.delete.visibility = View.INVISIBLE
                holder.image.visibility = View.INVISIBLE
                holder.folderName.visibility = View.INVISIBLE

                holder.add.setOnClickListener {
                    (ctx as BookmarkEditActivity).addDialog()
                }
            }
            // 다른 리싸이클러뷰
        } else {
            dataList[position].let { item ->
                Glide.with(ctx)
                    .load(item.thumb)
                    .apply(options)
                    .into(holder.image)
                holder.folderName.setText(item.categoryName)

                holder.image.setOnClickListener{
                    GlobalData.editFolderName = holder.folderName.text.toString()
                    GlobalData.updateFlipId = dataList[position].category_id

                    (ctx as BookmarkEditActivity).editDialog(position)
                }

                holder.delete.setOnClickListener {
                    try {
                        if (dataList[position].category_id == "") {
                            GlobalData.addFlip.remove(holder.folderName.text.toString())

                        } else {
                            // deleteList에 추가
                            GlobalData.deleteFlip.add(dataList[position].category_id)
                            Log.e("deleteFlipList", GlobalData.deleteFlip.toString())
                        }

                        dataList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, dataList.size)

                    } catch (e: IndexOutOfBoundsException) {
                        Log.e("Index error", e.toString())
                    }
                }
            }
        }
    }

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }

    fun changeItem(position: Int, flipsName: String) {
        dataList[position].categoryName = flipsName
        notifyItemChanged(position)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val add: ImageView = itemView.findViewById(R.id.iv_rv_item_bookmark_edit_folder_add)
        val delete: ImageView = itemView.findViewById(R.id.iv_rv_item_bookmark_edit_folder_delete)
        val image: ImageView = itemView.findViewById(R.id.iv_rv_item_bookmark_edit_folder_image)
        val folderName: EditText = itemView.findViewById(R.id.edt_rv_item_bookmark_edit_folder_name)
    }
}