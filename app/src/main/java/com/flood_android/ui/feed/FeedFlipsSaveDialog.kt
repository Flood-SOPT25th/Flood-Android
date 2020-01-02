package com.flood_android.ui.feed

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.feed.adapter.FeedSaveFlipsCategoryRVAdapter
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.dialog_feed_save_flips.*

class FeedFlipsSaveDialog(private val postIdx : String, private var ivFlip : ImageView) : RoundedBottomSheetDialogFragment(), View.OnClickListener {
    private val networkService: NetworkServiceUser by lazy {
        ApplicationController.networkServiceUser
    }

    var token = ""
    override fun onClick(p0: View?) {
    }

    lateinit var adapter: FeedSaveFlipsCategoryRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        token  = SharedPreferenceController.getAuthorization(context!!)!!

        return inflater.inflate(R.layout.dialog_feed_save_flips, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView(){
        getCategoryResponse()
    }

    /**
     *  플립 카테고리 서버 통신
     */
    private fun getCategoryResponse() {
        networkService.getPostBookmarkResponse(token).safeEnqueue({},
            onSuccess = {
                setCategoryRecyclerView(it.data.categorys, ivFlip)
            })
    }

    /**
     *  플립 카테고리 리사이클러뷰 설정
     */
    private fun setCategoryRecyclerView(dataList: ArrayList<BookmarkData>, iv_flip: ImageView) {
        var feedSaveFlipsCategoryRVAdapter = FeedSaveFlipsCategoryRVAdapter(context!!, dataList, postIdx, iv_flip)
        rv_dialog_feed_save_flips_category.apply {
            adapter = feedSaveFlipsCategoryRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}