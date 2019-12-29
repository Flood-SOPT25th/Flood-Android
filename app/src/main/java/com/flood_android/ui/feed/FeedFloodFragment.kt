package com.flood_android.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.ui.feed.adapter.FeedRVAdapter
import com.flood_android.ui.feed.adapter.FeedTop3RVAdapter
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.ui.feed.data.FeedTop3Data
import com.flood_android.ui.feed.data.GetAllFeedResponse
import com.flood_android.ui.feed.data.GetFeedTop3Response
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_feed_flood.*
import kotlinx.android.synthetic.main.toast_feed_save_flips_category.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FeedFloodFragment : Fragment() {
    val networkService: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }
    var token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58"

    lateinit var feedRVAdapter : FeedRVAdapter
    lateinit var feedTop3RVAdapter: FeedTop3RVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed_flood, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
    }

    private fun initView(){
        setWeek()
        getTop3Response()
        getTodayResponse()
    }

    private fun setWeek(){
        var sdf  = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        tv_feed_flood_top3_date.text = getWeekOfMonth(sdf.format(Date()))
    }

    /**
     *  몇째 주, 몇월인지  구하기
     */
    private fun getWeekOfMonth(date : String): String{
        var calendar : Calendar = Calendar.getInstance()
        var dates : List<String> = date.split("-")
        var year : Int = Integer.parseInt(dates[0])
        var monthNum : Int = Integer.parseInt(dates[1])
        var day : Int = Integer.parseInt(dates[2])
        calendar.set(year, monthNum-1, day)
        var weekNum :Int = calendar.get(Calendar.WEEK_OF_MONTH)
        var week  = ""
        when (weekNum){
            1 -> week = "1st"
            2 -> week = "2nd"
            3 -> week = "3rd"
            else -> week = weekNum.toString().plus("th")
        }
        var month = ""
        when (monthNum){
            1-> month = "January"
            2 -> month = "February"
            3-> month = "March"
            4-> month = "April"
            5-> month = "May"
            6-> month = "June"
            7-> month = "July"
            8-> month = "August"
            9-> month = "September"
            10-> month = "October"
            11-> month = "November"
            12-> month = "December"
        }

        return week.plus(" ").plus(month)
    }

    /**
     *  Top3 서버 통신
     */
    private val onTop3Success : (GetFeedTop3Response) -> Unit = {
        Log.v("Feed", it.data.topArr[0].user_name)
        setTop3RecyclerView(it.data.topArr)
    }
    private val onTop3Failure : (Throwable) -> Unit = {
        Log.v("Postygyg", it.toString())
    }
    private fun getTop3Response(){
        networkService.getFeedTop3Response(token).safeEnqueue(onTop3Failure, onTop3Success)
    }

    private fun setTop3RecyclerView(top3DataList : ArrayList<FeedTop3Data>) {
        feedTop3RVAdapter = FeedTop3RVAdapter(context!!, top3DataList)
        rv_feed_flood_top3.apply {
            adapter = feedTop3RVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
        feedTop3RVAdapter.notifyDataSetChanged()
    }

    /**
     *  모든 게시물 조회
     */
    private val onAllFeedSuccess : (GetAllFeedResponse) -> Unit = {
        setTodayRecyclerView(it.data.pidArr)
    }

    private fun getTodayResponse(){
        networkService.getAllFeedResponse(token).safeEnqueue({}, onAllFeedSuccess)
    }

    // Today 리사이클러뷰
    private fun setTodayRecyclerView(dataList : ArrayList<FeedData>){
        feedRVAdapter =  FeedRVAdapter(context!!, dataList)
        rv_feed_flood_today.apply {
            adapter =feedRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }


    /**
     *  페이징 처리하기!!!!!!!!!!!!!!!!!!!
     */


}