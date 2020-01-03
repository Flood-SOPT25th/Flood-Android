package com.flood_android.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.feed.adapter.FeedRVAdapter
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.ui.mypage.adapter.MypageMypostRVAdapter
import com.flood_android.util.GifDrawableImageViewTarget
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_mypage_mypost.*

class MypageMypostActivity : AppCompatActivity() {

    lateinit var mypageMypostRVAdapter: MypageMypostRVAdapter

    val networkService: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_mypost)
        initView()
    }

    private fun initView() {
        tv_mypage_mypost_cnt.text = intent.getStringExtra("cnt")

        btn_mypage_mypost_back.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                finish()
            }
        })
        getMypostResponse()
        pagination()
    }

    /**
     *  내가 쓴 글 리사이클러뷰 설정
     */
    private fun setMypostRecyclerView(dataList: ArrayList<FeedData>) {
        mypageMypostRVAdapter = MypageMypostRVAdapter(this@MypageMypostActivity, dataList)
        rv_mypage_mypost.apply {
            adapter = mypageMypostRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }

    /**
     * 내가 쓴 글 조회 서버 통신
     */
    private fun getMypostResponse() {
        networkService.getMyPostResponse(
            SharedPreferenceController.getAuthorization(this@MypageMypostActivity)!!,
            0,
            10
        )
            .safeEnqueue(
                onSuccess = {
                    setMypostRecyclerView(it.data.pidArr)
                }
            )
    }

    /**
     *  내 게시글 리사이클러뷰 페이징 처리하기
     */
    private fun pagination() {
        nsv_mypage_mypost.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                if (!nsv_mypage_mypost.canScrollVertically(1)) {

                    iv_mypage_mypost_loading.visibility = View.VISIBLE
                    Glide.with(this@MypageMypostActivity).load(R.drawable.loading_blue).into(
                        GifDrawableImageViewTarget(iv_mypage_mypost_loading,1)
                    )

                    val itemTotalCount: Int = rv_mypage_mypost.adapter!!.itemCount
                    networkService.getAllFeedResponse(
                        SharedPreferenceController.getAuthorization(
                            this@MypageMypostActivity
                        )!!, 0, itemTotalCount + 10
                    ).safeEnqueue({},
                        onSuccess = {

                            Log.v("**현주-내 게시글", "페이징 성공")
                            mypageMypostRVAdapter.dataList = it.data.pidArr
                            mypageMypostRVAdapter.notifyDataSetChanged()
                            nsv_mypage_mypost.fullScroll(NestedScrollView.FOCUS_DOWN)

                            val handler = Handler()
                            handler.postDelayed(Runnable {
                                iv_mypage_mypost_loading.visibility = View.GONE
                            }, 2000)
                        }
                    )
                }
            }
        })
    }

}
