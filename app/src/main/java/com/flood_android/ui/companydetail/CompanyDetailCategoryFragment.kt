package com.flood_android.ui.companydetail


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceCompany
import com.flood_android.ui.feed.adapter.FeedRVAdapter
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_company_detail_category.*
import kotlinx.android.synthetic.main.fragment_feed.*

class CompanyDetailCategoryFragment(var groupCode: String, val categoryName: String = "") : Fragment() {


    val networkService: NetworkServiceCompany by lazy {
        ApplicationController.networkServiceCompany
    }
    lateinit var companyFeedRVAdapter: CompanyFeedRVAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v("청하", "피드 프래그먼트")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_detail_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        getCategoryFeedResponse(groupCode, categoryName)
    }

    private val onGetFeedSuccess: (GetCompanyDetailFeedResponse) -> Unit = { response ->
        Log.v("청하", "통신 성공")
        setFeedRecyclerView(response.data.groupArr)
    }

    fun getCategoryFeedResponse(groupCode: String, category: String) {
        networkService.getCompanyDetailFeedResponse(SharedPreferenceController.getAuthorization(context!!).toString(), groupCode, category).safeEnqueue({}, onGetFeedSuccess)
    }

    private fun setFeedRecyclerView(feedDataList: ArrayList<FeedData>) {
        companyFeedRVAdapter = CompanyFeedRVAdapter(context!!, feedDataList)
        rv_company_feed_category_news.apply {
            adapter = companyFeedRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }


}
