package com.flood_android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.bookmarkedit.BookmarkEditActivity
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.ui.mypage.adapter.MypageMyFlipRVAdapter
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : Fragment() {

    val networkService: NetworkServiceUser by lazy {
        ApplicationController.networkServiceUser
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get data from server here 통신이 들어올 때마다 notify 될 것
        //서버에서 데이터 객체 받아와서 성공 시 extension 함수 이용해 호출하고 받은 데이터 객체를
        //ApplicationController.instance.networkServiceUser.enqueue(mypageMyflipdata)

        initView()

        btn_mypage_myflips.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                var intent = Intent(context, BookmarkEditActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun initView(){
        getUserFlipResponse()
    }


    /**
     *  유저 북마크 서버 통신
     */
    private fun getUserFlipResponse(){
        networkService.getPostBookmarkResponse(SharedPreferenceController.getAuthorization(context!!)!!).safeEnqueue(
            onSuccess = {
                setMyFlipRecyclerView(it.data.categorys)
            }
        )
    }

    /**
     * MyFlip(유저 북마크) 리사이클러뷰 설정
     */
    private fun setMyFlipRecyclerView(dataList: ArrayList<BookmarkData>) {
        rv_mypage_myflip.isNestedScrollingEnabled = false
        rv_mypage_myflip.setHasFixedSize(false)
        if (dataList.size == 0){
            rv_mypage_myflip.visibility = View.GONE
        }
        rv_mypage_myflip.apply {
            adapter = MypageMyFlipRVAdapter(context, dataList)
            layoutManager = GridLayoutManager(activity!!, 2)
        }
    }
}