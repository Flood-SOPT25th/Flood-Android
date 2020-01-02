package com.flood_android.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.feed.adapter.FeedRVAdapter
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.ui.mypage.adapter.MypageMypostRVAdapter
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_mypage_mypost.*

class MypageMypostActivity : AppCompatActivity() {

    val networkService: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_mypost)
        initview()
    }
    private fun initview(){
        btn_mypage_mypost_back.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                finish()
            }
        })

        getMypostResponse()
    }

    /**
     *  내가 쓴 글 리사이클러뷰 설정
     */
    private fun setMypostRecyclerView(dataList : ArrayList<FeedData>){
        rv_mypage_mypost.apply {
            adapter = MypageMypostRVAdapter(context, dataList)
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }

    /**
     * 내가 쓴 글 조회 서버 통신
     */
    private fun getMypostResponse(){
        networkService.getMyPostResponse(SharedPreferenceController.getAuthorization(this@MypageMypostActivity)!!).safeEnqueue(
            onSuccess = {
                setMypostRecyclerView(it.data.pidArr)
                tv_mypage_mypost_cnt.text = it.data.pidArr.size.toString()
            }
        )
    }

}
