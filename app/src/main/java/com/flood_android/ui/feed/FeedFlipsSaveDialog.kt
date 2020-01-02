package com.flood_android.ui.feed

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.deishelon.roundedbottomsheet.RoundedBottomSheetDialogFragment
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.feed.adapter.FeedSaveFlipsCategoryRVAdapter
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.ui.feed.data.GetPostBookmarkResponse
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.dialog_feed_save_flips.*

class FeedFlipsSaveDialog : RoundedBottomSheetDialogFragment(), View.OnClickListener {
    private val networkService: NetworkServiceUser by lazy {
        ApplicationController.networkServiceUser
    }

    var token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58"//SharedPreferenceController.getAuthorization(context!!)!!

    override fun onClick(p0: View?) {

    }

    lateinit var adapter: FeedSaveFlipsCategoryRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
     *  피드 카테고리 서버 통신
     */
    private var successGetBookmarkCategory : (GetPostBookmarkResponse)  -> Unit  = {
        setCategoryRecyclerView(it.data.categorys)

    }

    private fun getCategoryResponse() {
        networkService.getPostBookmarkResponse(token).safeEnqueue({}, successGetBookmarkCategory)
    }

    private fun setCategoryRecyclerView(dataList: ArrayList<BookmarkData>) {
        var feedSaveFlipsCategoryRVAdapter = FeedSaveFlipsCategoryRVAdapter(context!!, dataList)
        rv_dialog_feed_save_flips_category.apply {
            adapter = feedSaveFlipsCategoryRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}