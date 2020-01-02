package com.flood_android.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.MypageFragment
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.network.NetworkServiceUser
import com.flood_android.ui.alarm.AlarmFragment
import com.flood_android.ui.company.CompanyFragment
import com.flood_android.ui.feed.FeedFragment
import com.flood_android.ui.feed.adapter.FeedSaveFlipsCategoryRVAdapter
import com.flood_android.ui.feed.data.BookmarkData
import com.flood_android.ui.feed.data.GetPostBookmarkResponse
import com.flood_android.ui.feed.data.PostBookmarkAddData
import com.flood_android.ui.feed.data.PostBookmarkCancelData
import com.flood_android.util.safeEnqueue
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.Holder
import com.orhanobut.dialogplus.ViewHolder
import com.flood_android.ui.postnourl.PostNoUrlActivity
import com.flood_android.util.GlobalData
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_feed_save_flips.*

class MainActivity : AppCompatActivity() {
    lateinit var dialog: DialogPlus

    val networkServiceFeed: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }
    val networkServiceUser: NetworkServiceUser by lazy {
        ApplicationController.networkServiceUser
    }

    private val feedFragment = FeedFragment()
    private val companyFragment = CompanyFragment()
    private val alarmFragment = AlarmFragment()
    private val mypageFragment = MypageFragment()
    private var active: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        addFragment(feedFragment)
        addFragment(companyFragment)
        addFragment(alarmFragment)
        addFragment(mypageFragment)
        showFragment(feedFragment)

        iv_main_tab_feed.isSelected = true

        iv_main_tab_feed.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                iv_main_tab_feed.isSelected = true
                iv_main_tab_company.isSelected = false
                iv_main_tab_mypage.isSelected = false
                iv_main_tab_alarm.isSelected = false
                showFragment(feedFragment)
            }
        })
        iv_main_tab_company.setOnClickListener(object  : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                iv_main_tab_feed.isSelected = false
                iv_main_tab_company.isSelected = true
                iv_main_tab_mypage.isSelected = false
                iv_main_tab_alarm.isSelected = false
                showFragment(companyFragment)
            }
        })
        iv_main_tab_write.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                val intent = Intent(this@MainActivity, PostNoUrlActivity::class.java)
                startActivity(intent)
            }
        })
        iv_main_tab_alarm.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                iv_main_tab_feed.isSelected = false
                iv_main_tab_company.isSelected = false
                iv_main_tab_mypage.isSelected = false
                iv_main_tab_alarm.isSelected = true
                showFragment(alarmFragment)
            }
        })

        iv_main_tab_mypage.setOnClickListener(object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                iv_main_tab_feed.isSelected = false
                iv_main_tab_company.isSelected = false
                iv_main_tab_mypage.isSelected = true
                iv_main_tab_alarm.isSelected = false
                showFragment(mypageFragment)
            }
        })
    }

    fun addFragment(fragment: Fragment){
        val fm : FragmentManager = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(R.id.fl_main, fragment).hide(fragment)
        transaction.commit()
    }

    fun showFragment(fragment: Fragment){
        if (active == null) active = fragment

        val fm : FragmentManager = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.hide(active!!).show( fragment)
        transaction.commit()
        active = fragment
    }

    /**
     *  댓글, 포스트 날짜 계산
     */
    fun calculateTime(postTimeDate: String): String {

        var dateList: List<String> = postTimeDate.split("T")
        var date: String = dateList[0]
        var timeList: List<String> = dateList[1].split(".")
        var time: String = timeList[0]

        var formattedServerTime: String = date.plus(" ").plus(time)

        return formattedServerTime
//        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        var formattedPostTime = dateFormat.format(formattedServerTime)
//        postTime?: try {
//            postTime = dateFormat.parse(formattedPostTime).time
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//
//        Log.d("현주 시간 계산", postTime.toString())
//
//        var curTime: Long = System.currentTimeMillis()
//
//        var diff: Long = curTime - postTime!!
//
//        var gapMin: Long = diff / 60000
//        var gapHour: Long = gapMin / 60
//        var gapDay: Long = gapHour / 24
//        var gapWeek: Long = gapDay / 7
//        var gapMonth: Long = gapDay / 30
//        var gapYear: Long = gapDay / 365
//
//        if (gapYear >= 1) {
//            return gapYear.toInt().toString() + "년 전"
//        } else {
//            if (gapMonth >= 1) {
//                return gapMonth.toInt().toString() + "달 전"
//            } else {
//                if (gapWeek >= 1) {
//                    return gapWeek.toInt().toString() + "주 전"
//                } else {
//                    if (gapDay >= 1) {
//                        return gapDay.toInt().toString() + "일 전"
//                    } else {
//                        if (gapHour >= 1) {
//                            return gapHour.toInt().toString() + "시간 전"
//                        } else {
//                            return gapMin.toInt().toString() + "분 전"
//                        }
//                    }
//                }
//            }
//        }
        }

    /**
     *  북마크 추가 서버 통신
     */
    fun postBookmarkAddRequest(token: String, post_id: String, category_id:String, imageView: ImageView){
        networkServiceFeed.postBookmarkAddRequest(token, PostBookmarkAddData(post_id, category_id)).safeEnqueue({},
            onSuccess = {
                imageView.isSelected = true
                GlobalData.bottomSheetDialogFragment!!.dismiss()
                GlobalData.bottomSheetDialogFragment = null
                Log.v("MainActivity", "북마크 추가 통신 성공")
            }
        )
    }

    /**
     *  북마크 취소 서버 통신
     */
    fun postBookmarkCancelRequest(token : String, post_id : String){
        networkServiceFeed.postBookmarkCancelRequest(token, PostBookmarkCancelData(post_id)).safeEnqueue({},
            onSuccess = {
                Log.v("MainActivity", "북마크 취소 통신 성공")
            })
    }

//    /**
//     *  북마크 리스트 받아오기
//     */
//    fun getBookmarkListResponse(token : String){
//        networkServiceUser.getPostBookmarkResponse(token).safeEnqueue ({},
//            onSuccess = { response->
//                setFlipCategoryRecyclerView(response.data.categorys)
//            })
//    }

    /**
     *  플립에 저장하기 다이얼로그 띄우기
     */
//    fun makeFlipDialog(ivSelector: ImageView) {
//        ivSelector.isSelected = true
//
//        val holder: Holder = ViewHolder(R.layout.dialog_feed_save_flips)
//
//
//        dialog = DialogPlus.newDialog(this@MainActivity)
//            .apply {
//                setContentHolder(holder)
//                setGravity(Gravity.BOTTOM)
//            }
//            .setOnCancelListener {
//                ivSelector.isSelected = false
//            }
//            .setOnBackPressListener {
//                ivSelector.isSelected = false
//            }
//            .create()
//
//        getBookmarkListResponse(SharedPreferenceController.getAuthorization(this@MainActivity)!!)
//
//        dialog.show()
//    }

//    fun dismissFlipDialog() {
//        dialog.dismiss()
//    }
//
//    /**
//     *  플립 카테고리 리사이클러뷰 설정
//     */
//    private fun setFlipCategoryRecyclerView(dataList: ArrayList<BookmarkData>) {
//        val feedSaveFlipsCategoryRVAdapter = FeedSaveFlipsCategoryRVAdapter(this, dataList)
//        rv_dialog_feed_save_flips_category.apply {
//            adapter = feedSaveFlipsCategoryRVAdapter
//            layoutManager =
//                LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
//        }
//        //feedSaveFlipsCategoryRVAdapter.notifyDataSetChanged()
//    }
}
