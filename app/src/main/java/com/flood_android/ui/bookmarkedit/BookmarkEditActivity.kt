package com.flood_android.ui.bookmarkedit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.ui.bookmarkedit.post.CategoryObejct
import com.flood_android.ui.bookmarkedit.post.PostFlipRequest
import com.flood_android.ui.bookmarkedit.post.PostFlipResponse
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.util.GlobalData
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_bookmark_edit.*

class BookmarkEditActivity : AppCompatActivity(), View.OnClickListener {

    private val bookmarkNewFlipDialog by lazy {
        BookmarkNewFlipDialog()
    }
    private val bookmarkEditNameDialog by lazy {
        BookmarkEditNameDialog()
    }

    override fun onClick(v: View?) {
        when (v) {
            v!!.findViewById<ImageView>(R.id.iv_rv_item_bookmark_edit_folder_image) -> {
                val idx = rv_bookmark_edit_list.getChildAdapterPosition(v.parent as View)
                Log.v("Bookfbdbbbmark", idx.toString())
            }
        }
    }

    lateinit var bookmarkEditFolderRVAdapter: BookmarkEditFolderRVAdapter
    lateinit var updateList: ArrayList<BookmarkEditFolderData>
    lateinit var originList: ArrayList<BookmarkEditFolderData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark_edit)

        updateList = ArrayList()
        originList = ArrayList()

        iv_bookmark_edit_back.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                finish()
            }
        })

        tv_bookmark_edit_done.setOnClickListener {
            GlobalData.categoryObejct =
                CategoryObejct(GlobalData.addFlip, GlobalData.deleteFlip, GlobalData.updateFlips)
            postFlip(
                SharedPreferenceController.getAuthorization(this@BookmarkEditActivity)!!,
                GlobalData.categoryObejct
            )
        }

        getPostBookmarkResponse()

    }

    var fail: (Throwable) -> Unit = {
        Log.v("BookmarkEditActivity", "fail")
        Log.v("BookmarkEditActivity", it.message.toString())
        Log.v("BookmarkEditActivity", it.toString())
    }
    var temp: (PostFlipResponse) -> Unit = {
        Log.v("BookmarkEditActivity", "temp")
        Log.v("BookmarkEditActivity", it.message)

        finish()
    }

    private fun postFlip(
        token: String,
        categoryObejct: CategoryObejct
    ) {
        val postFlipResponse = ApplicationController.networkServiceUser
            .postFlipResponse("application/json", token, PostFlipRequest(categoryObejct))
        postFlipResponse.safeEnqueue(fail, temp)
    }


    private fun getPostBookmarkResponse() {
        val getPostBookmarkResponse = ApplicationController.networkServiceUser
            .getPostBookmarkResponse(SharedPreferenceController.getAuthorization(this@BookmarkEditActivity).toString())
        getPostBookmarkResponse.safeEnqueue {
            if (it.message == "북마크 조회 완료") {
                var dataList: ArrayList<BookmarkData> = it.data.categorys
                setRecyclerView(dataList)
            }
        }

    }

    private fun setRecyclerView(dataList: ArrayList<BookmarkData>) {
        bookmarkEditFolderRVAdapter = BookmarkEditFolderRVAdapter(this, dataList)
        bookmarkEditFolderRVAdapter.setOnItemClickListener(this)
        rv_bookmark_edit_list.adapter = bookmarkEditFolderRVAdapter
        rv_bookmark_edit_list.layoutManager = GridLayoutManager(this, 2)
    }

    fun addDialog() {
        Log.e("addDialog", "addDialog")
        bookmarkNewFlipDialog.show(supportFragmentManager, "bookmarkEditNameDialog")
    }

    fun editDialog(position: Int) {
        Log.e("editDialog", "editDialog")
        var bundle = Bundle()
        bundle.putInt("position", position)
        bookmarkEditNameDialog.arguments = bundle
        bookmarkEditNameDialog.show(supportFragmentManager, "bookmarkEditNameDialog")
    }

    fun addItem(folderName: String) {
        if (true) {
            bookmarkEditFolderRVAdapter.dataList.add(1, BookmarkData("", folderName, "", 0))
            bookmarkEditFolderRVAdapter.notifyItemInserted(1)
        }
    }

    fun setUpdateFolderName(position: Int, changedName: String) {
        bookmarkEditFolderRVAdapter.changeItem(position, changedName)
    }


}
