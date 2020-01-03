package com.flood_android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.bookmarkedit.BookmarkEditActivity
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.ui.mypage.MypageMypostActivity
import com.flood_android.ui.mypage.adapter.MypageMyFlipRVAdapter
import com.flood_android.ui.myprofile.MyProfileEditActivity
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_mypage.*


class MypageFragment : Fragment() {

    var mypostCnt : String = " "

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
        initView()

        btn_mypage_myflips.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                var intent = Intent(context, BookmarkEditActivity::class.java)
                startActivity(intent)
            }
        })

        btn_mypage_mypost_arrow.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                startMypostActivity()
            }
        })
        imgbtn_mypage_mypost.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                startMypostActivity()
            }
        })
        tv_mypage_mypost_cnt.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                startMypostActivity()
            }
        })

        btn_mypage_user_setting.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                val intent = Intent(context, MyProfileEditActivity::class.java)
                startActivity(intent)
            }
        })
      
        iv_mypage_user_setting.setOnClickListener {
            val intent = Intent(context, MyProfileEditActivity::class.java)
            intent.putExtra("cnt", mypostCnt)
            startActivity(intent)
        }
    }

    private fun initView(){
        getUserFlipResponse()
        getUserInfoResponse()
    }

    private fun startMypostActivity(){
        val intent = Intent(context, MypageMypostActivity::class.java)
        intent.putExtra("cnt", mypostCnt)
        startActivity(intent)
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


    /**
     *  유저 정보 서버 통신
     */
    private fun getUserInfoResponse(){
        networkService.getMyPageUserInfo(SharedPreferenceController.getAuthorization(context!!)!!).safeEnqueue(
            onSuccess = {res->
                res.data.userInfo.let{
                    tv_mypage_username.text = it.name
                    val position = it.rank.plus(" ").plus(it.groupDepartment)
                    tv_mypage_userinfo.text = position
                    Glide.with(context!!)
                        .load(it.profileImage)
                        .transform(CenterCrop(), CircleCrop())
                        .into(iv_circular_mypage_profile)

                    mypostCnt = it.count.toString()
                    tv_mypage_mypost_num.text = mypostCnt
                }
            }
        )
    }
}