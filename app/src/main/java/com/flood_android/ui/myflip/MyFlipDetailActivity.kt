package com.flood_android.ui.myflip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.companydetail.GetCompanyDetailFeedResponse
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_my_flip_detail.*

class MyFlipDetailActivity : AppCompatActivity() {

    val networkService: NetworkServiceUser by lazy {
        ApplicationController.networkServiceUser
    }

    lateinit var myFlipFeedRVAdapter: MyFlipFeedRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_flip_detail)

        val flipIdx = intent.getStringExtra("categoryIdx")
        getMyflipDetail(flipIdx)

        iv_myflip_detail_back.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                finish()
            }
        })
    }

    private val onGetFlipFeedSuccess: (GetMyFlipDetailResponse) -> Unit = { response ->
        Log.v("청하", "통신 성공")
        setFeedRecyclerView(response.data.posts)
    }

    private fun getMyflipDetail(filp: String) {
        networkService.getMyFlipDetailResponse(
            SharedPreferenceController.getAuthorization(this@MyFlipDetailActivity)!!, filp).safeEnqueue({}, onGetFlipFeedSuccess)

    }

    private fun setFeedRecyclerView(feedDataList: ArrayList<FeedData>) {
        myFlipFeedRVAdapter = MyFlipFeedRVAdapter(this@MyFlipDetailActivity, feedDataList)
        rv_myflip_detail_list.apply {
            adapter = myFlipFeedRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }


}
