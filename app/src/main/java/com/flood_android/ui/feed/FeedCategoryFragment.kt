package com.flood_android.ui.feed


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.flood_android.R
import com.flood_android.ui.feed.adapter.FeedRVAdapter
import com.flood_android.ui.feed.data.FeedData
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedCategoryFragment : Fragment() {

    var feedDataList: ArrayList<FeedData> = ArrayList()
    lateinit var feedRVAdapter: FeedRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed_category, container, false)
    }

    /**
     *  페이징 처리하기!!!!!!!!!!!!!!!!!!!1
     */

    private fun setFeedRecyclerView() {
        feedRVAdapter = FeedRVAdapter(context!!, feedDataList)
        rv_feed_category_news.apply {
            adapter =feedRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }
}
