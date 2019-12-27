package com.flood_android.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.flood_android.MypageFragment
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.ui.alarm.AlarmFragment
import com.flood_android.ui.company.CompanyFragment
import com.flood_android.ui.feed.FeedFragment
import com.flood_android.ui.feed.data.PostBookmarkCancelResponse
import com.flood_android.ui.post.PostActivity
import com.flood_android.ui.write.WriteActivity
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val networkService: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }
    var token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(FeedFragment())

        iv_main_tab_feed.setOnClickListener{
            replaceFragment(FeedFragment())
        }
        iv_main_tab_company.setOnClickListener{
            replaceFragment(CompanyFragment())
        }
        iv_main_tab_write.setOnClickListener{
            val intent = Intent(this@MainActivity, PostActivity::class.java)
            startActivity(intent)
        }
        iv_main_tab_alarm.setOnClickListener{
            replaceFragment(AlarmFragment())
        }
        iv_main_tab_mypage.setOnClickListener{
            replaceFragment(MypageFragment())
        }
    }

    fun addFragment(fragment: Fragment){
        val fm : FragmentManager = supportFragmentManager
        val transaction = fm.beginTransaction()
        // 이 아이디 자리에, 어떤 프래그먼트를 넣어주겠다.
        transaction.add(R.id.fl_main, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment){
        val fm : FragmentManager = supportFragmentManager
        val transaction = fm.beginTransaction()
        // 이 아이디 자리에, 어떤 프래그먼트를 넣어주겠다.
        transaction.replace(R.id.fl_main, fragment)
        transaction.commit()
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

//    /**
//     *  북마크 취소 서버 통신
//     */
//    private var successPostBookmarkCancelSuccess : (PostBookmarkCancelResponse)  -> Unit  = {
//
//    }
//
//    fun postBookmarkCancelResponse(token : String){
//
//    }
}
