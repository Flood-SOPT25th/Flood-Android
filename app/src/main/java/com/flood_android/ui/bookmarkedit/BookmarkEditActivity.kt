package com.flood_android.ui.bookmarkedit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.ui.bookmarkedit.post.CategoryObejct
import com.flood_android.ui.bookmarkedit.post.PostFlipRequest
import com.flood_android.ui.bookmarkedit.post.PostFlipResponse
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.util.GlobalData
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_bookmark_edit.*
import kotlinx.android.synthetic.main.dialog_bookmark_edit_name.*
import okhttp3.RequestBody

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

    lateinit var postList: ArrayList<BookmarkEditFolderUpdateData>
    // 이런 ㄹㅇ 서버한테 줄 배열. 일단 임시로 만들어 놓음.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark_edit)

        updateList = ArrayList()
        originList = ArrayList()
//        setRecyclerView()

        //init()
        /*cl_bookmark_edit_background.setOnClickListener {
            if (bookmarkEditFolderRVAdapter.clickedposition == -10){

            }
            else {
                updateList.add(bookmarkEditFolderRVAdapter.clickedposition, )
            }
        }*/

        //맨 처음 이 액티비티에 들어왔을 때.
        //updateList = originList = rv_book
        //수정이 하나라도 들어갔을 때(add update delete)
        //rv_book = update  !=  origin
        //
        //complete
        //update, 수정 된 이름을 반영하려면
        //그냥 리스트 사이즈만큼 for 돌리면서 이름을 족족 넣어주면 된다.


        tv_bookmark_edit_done.setOnClickListener {
            // 플립 보내기
            GlobalData.categoryObejct =
                CategoryObejct(GlobalData.addFlip, GlobalData.deleteFlip, GlobalData.updateFlips)

            postFlip(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58",
                GlobalData.categoryObejct
            )


            //
            Log.e("flipadd", GlobalData.addFlip.toString())

            //origin과 update
            for (i in 0 until rv_bookmark_edit_list.size) {
                //내가 수정을 하면 아이디는 그대로이고 이름이 바뀜
                val edit =
                    rv_bookmark_edit_list[i].findViewById<EditText>(R.id.edt_rv_item_bookmark_edit_folder_name)

                //updateList[i].folderName = edit.text.toString()
                //바뀐 이름이 족족 들어감.
                //동시에 아이디 - 이미지 - 이름 쌍이 완성
                //이제 이거랑 origin 비교

            }

            //origin, update가 다름.

            //origin, update사이즈 비교
            //origin이 더 크다고 치

            val realUpdated = updateList.filter { !originList.contains(it) }


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
            .getPostBookmarkResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58")
        //.getPostBookmarkResponse(SharedPreferenceController.getAuthorization(this@BookmarkEditActivity).toString())
        getPostBookmarkResponse.safeEnqueue {
            if (it.message == "북마크 조회 완료") {
                var dataList: ArrayList<BookmarkData> = it.data.categorys
                setRecyclerView(dataList)
            }
        }

    }

/*    private fun init(){
        originList.add(BookmarkEditFolderData(0, "", "AR"))
        originList.add(
            BookmarkEditFolderData(
                1,
                "http://www.kyeongin.com/mnt/file/201905/20190504000854504_1.jpg",
                "스타트업"
            )
        )
        originList.add(BookmarkEditFolderData(2, "", "마케팅"))
        originList.add(BookmarkEditFolderData(3, "", "블록체인"))
        originList.add(
            BookmarkEditFolderData(
                4,
                "http://www.kyeongin.com/mnt/file/201905/20190504000854504_1.jpg",
                "클라우드"
            )
        )
        originList.add(
            BookmarkEditFolderData(
                5,
                "http://www.kyeongin.com/mnt/file/201905/20190504000854504_1.jpg",
                "카스"
            )
        )
        originList.add(
            BookmarkEditFolderData(
                6,
                "http://www.kyeongin.com/mnt/file/201905/20190504000854504_1.jpg",
                "테라"
            )
        )

        updateList = originList

        bookmarkEditFolderRVAdapter = BookmarkEditFolderRVAdapter(this, originList)
        bookmarkEditFolderRVAdapter.setOnItemClickListener(this)
        rv_bookmark_edit_list.adapter = bookmarkEditFolderRVAdapter
        rv_bookmark_edit_list.layoutManager = GridLayoutManager(this, 2)


    }*/

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
            val position = bookmarkEditFolderRVAdapter.itemCount
            bookmarkEditFolderRVAdapter.dataList.add(1, BookmarkData("", folderName, "", 0))
            bookmarkEditFolderRVAdapter.notifyItemInserted(1)
            //bookmarkEditFolderRVAdapter.notifyItemRangeChanged(1, position)
            //bookmarkEditFolderRVAdapter.notifyDataSetChanged()
            //bookmarkEditFolderRVAdapter.notifyItemMoved(position, 1)

//            updateList.add(BookmarkEditFolderData(-1, "", ""))

        }
    }

    fun setUpdateFolderName(position: Int, changedName: String) {
        bookmarkEditFolderRVAdapter.changeItem(position, changedName)
    }


}
