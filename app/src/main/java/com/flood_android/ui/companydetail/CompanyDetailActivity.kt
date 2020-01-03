package com.flood_android.ui.companydetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceCompany
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.company.CompanyRVAdapter
import com.flood_android.ui.company.GroupArr
import com.flood_android.ui.feed.FeedCategoryFragment
import com.flood_android.ui.feed.adapter.FeedCategoryRVAdapter
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_company_detail.*
import kotlinx.android.synthetic.main.fragment_feed.*


class CompanyDetailActivity : AppCompatActivity() {

        private var feedFragments = listOf<Fragment>()
        set(value) {
            if (value.isEmpty()) return
            active = null
            value.forEach {
                addFragment(it)
            }
            showFragment(value.first())
            field = value
        }

    private lateinit var companyDetailCategoryRVAdapter: CompanyDetailCategoryRVAdapter
    private var active: Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_detail)

        var groupCode = intent.getStringExtra("code")
        // GET company 1차 통신
        getCompanyDetail(groupCode.toString())

        iv_company_detail_back.setOnClickListener {
            finish()
        }

    }

    private fun initView(dataList: List<String>) {

        companyDetailCategoryRVAdapter = CompanyDetailCategoryRVAdapter(this@CompanyDetailActivity, dataList) { position ->
            showFragment(feedFragments[position])
        }

        rv_company_detail_category_list.apply {
            adapter = this@CompanyDetailActivity.companyDetailCategoryRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    // GET company 1차 통신
    private fun getCompanyDetail(groupCode :String) {
        val getCompanyDetailResponse = ApplicationController.networkServiceCompany
            .getCompanyDetailResponse(SharedPreferenceController.getAuthorization(this@CompanyDetailActivity).toString(),
                groupCode)
        getCompanyDetailResponse.safeEnqueue {
            Log.v("postygyg", "postygyg1234")
            if (it.message == "그룹에 대한 정보") {
                Log.v("postygyg", "postygyg12342344")

                // 기업 아이콘과 기업명 뷰
                setTopView(it.data.groupInfo)
                // 카테고리 리싸이클러뷰
                setCategoryRecyclerView(it.data.groupInfo.category)
                successGetCategory(groupCode, it.data.groupInfo)
                initView(it.data.groupInfo.category)
            }
        }
    }

    // 기업 아이콘과 기업명 뷰
    private fun setTopView(data: GroupInfo) {
        Glide.with(this)
            .load(data.groupImage)
            .transform(CenterCrop(), CircleCrop())
            .into(iv_company_detail_company_logo)
        tv_company_detail_company_name.text = data.name
    }

    // 카테고리 리싸이클러뷰
    private fun setCategoryRecyclerView(dataList: List<String>) {
        Log.v("청하123", dataList.toString())
        companyDetailCategoryRVAdapter = CompanyDetailCategoryRVAdapter(this@CompanyDetailActivity, dataList)
        rv_company_detail_category_list.adapter = companyDetailCategoryRVAdapter
        rv_company_detail_category_list.layoutManager = LinearLayoutManager(this@CompanyDetailActivity, LinearLayoutManager.HORIZONTAL, false)
    }

    /**
     *  피드 카테고리 서버 통신
     */
    private fun successGetCategory(groupCode: String, data: GroupInfo){
        Log.v("청하", groupCode.toString())
        feedFragments = data.category.filterNot { it == "Flood" }.map { CompanyDetailCategoryFragment(groupCode, it) }

        companyDetailCategoryRVAdapter.dataList = data.category
        companyDetailCategoryRVAdapter.notifyDataSetChanged()
    }

    fun addFragment(fragment: Fragment){
        supportFragmentManager?.let { fm ->
            val transaction = fm.beginTransaction()
            // 이 아이디 자리에, 어떤 프래그먼트를 넣어주겠다.
            transaction.add(R.id.fl_company_detail_frag, fragment).hide(fragment)
            transaction.commit()
        }
    }

    fun showFragment(fragment: Fragment){
        supportFragmentManager?.let { fm ->
            if (active == null) active = fragment

            val transaction = fm.beginTransaction()
            transaction.hide(active!!).show(fragment)
            transaction.commit()
            active = fragment
        }
    }

    fun calculateTime(postTimeDate: String): String {

        var dateList: List<String> = postTimeDate.split("T")
        var date: String = dateList[0]
        var timeList: List<String> = dateList[1].split(".")
        var time: String = timeList[0]

        var formattedServerTime: String = date.plus(" ").plus(time)

        return formattedServerTime}
}
