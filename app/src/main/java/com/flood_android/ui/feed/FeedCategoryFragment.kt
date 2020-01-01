package com.flood_android.ui.feed


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.ui.feed.adapter.FeedRVAdapter
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.ui.feed.data.GetCategoryFeedResponse
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedCategoryFragment(val categoryName: String = "") : Fragment() {

    val networkService: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }

    lateinit var feedRVAdapter: FeedRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {
        getCategoryFeedResponse(categoryName)
    }

    /**
     *  페이징 처리하기!!!!!!!!!!!!!!!!!!!1
     */

    /**
     *  카테고리별 피드  서버 통신
     */
    private val onGetFeedSuccess: (GetCategoryFeedResponse) -> Unit = { response ->
        Log.v("현주", "통신 성공")
        Log.v("현주", response.data.pidArr.toString())
        Log.v("현주", response.data.toString())
        setFeedRecyclerView(response.data.pidArr)
    }

    fun getCategoryFeedResponse(category: String) {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58"
        networkService.getCategoryFeedResponse(token, category).safeEnqueue({}, onGetFeedSuccess)
    }

    /**
     *  피드 리사이클러뷰 설정하기
     */
    private fun setFeedRecyclerView(feedDataList: ArrayList<FeedData>) {
        feedRVAdapter = FeedRVAdapter(context!!, feedDataList)
        rv_feed_category_news.apply {
            adapter = feedRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }
}
