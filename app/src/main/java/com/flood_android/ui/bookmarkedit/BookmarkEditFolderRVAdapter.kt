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
import java.lang.IndexOutOfBoundsException
import android.widget.Toast
import android.view.View.OnFocusChangeListener
import com.flood_android.R


class BookmarkEditFolderRVAdapter(
    val ctx: Context,
    val dataList: ArrayList<BookmarkEditFolderData>

) : RecyclerView.Adapter<BookmarkEditFolderRVAdapter.Holder>() {
    var clickedposition = -10
    private var onItemClick : View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_item_bookmark_edit_folder, parent, false)
        view.findViewById<EditText>(R.id.edt_rv_item_bookmark_edit_folder_name).setOnClickListener(onItemClick)

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var options: RequestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(10))

        // 첫번째 버튼
        if(position == 0){
            dataList[position].let { item ->
                holder.add.visibility = View.VISIBLE
                holder.delete.visibility = View.INVISIBLE
                holder.image.visibility = View.INVISIBLE
                holder.folderName.visibility = View.INVISIBLE

                holder.add.setOnClickListener {
                    (ctx as BookmarkEditActivity).addItem()
                }
            }
            // 다른 리싸이클러뷰
        }else{
            var folderNameList = arrayListOf("")


            dataList[position].let { item ->
                Glide.with(ctx)
                    .load(item.image)
                    .apply(options)
                    .into(holder.image)
                holder.folderName.setText(item.folderName)
                //mStatusUpdateRunnable.run()
                /*holder.folderName.addTextChangedListener(object: TextWatcher{
                    override fun afterTextChanged(p0: Editable?) {

                    }
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        folderNameList.add(p0.toString())
                        Log.e("청하", folderNameList.toString())
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }
                })*/
                holder.delete.setOnClickListener{
                    try {
                        dataList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, dataList.size)
                    } catch (e: IndexOutOfBoundsException){
                        Log.e("Index error", e.toString())
                    }
                }
            }


        }
    }

    fun setOnItemClickListener(l: View.OnClickListener){
        onItemClick = l
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val add: ImageView = itemView.findViewById(R.id.iv_rv_item_bookmark_edit_folder_add)
        val delete: ImageView = itemView.findViewById(R.id.iv_rv_item_bookmark_edit_folder_delete)
        val image: ImageView = itemView.findViewById(R.id.iv_rv_item_bookmark_edit_folder_image)
        val folderName: EditText = itemView.findViewById(R.id.edt_rv_item_bookmark_edit_folder_name)
    }
}