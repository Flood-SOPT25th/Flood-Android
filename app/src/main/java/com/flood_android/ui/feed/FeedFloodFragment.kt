package com.flood_android.ui.feed

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.ui.feed.adapter.FeedRVAdapter
import com.flood_android.ui.feed.adapter.FeedTop3RVAdapter
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.ui.feed.data.FeedTop3Data
import com.flood_android.ui.feed.data.GetFeedTop3Response
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.fragment_feed_flood.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FeedFloodFragment : Fragment() {

    val networkService: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }

    lateinit var token: String
    lateinit var feedRVAdapter: FeedRVAdapter
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

    private fun initView() {
        token = SharedPreferenceController.getAuthorization(context!!)!!
        setWeek()
        getTop3Response()
        getTodayResponse(0, 5)
        setOnScrollChange()
    }

    private fun setWeek() {
        var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        tv_feed_flood_top3_date.text = getWeekOfMonth(sdf.format(Date()))
    }

    /**
     *  몇째 주, 몇월인지  구하기
     */
    private fun getWeekOfMonth(date: String): String {
        var calendar: Calendar = Calendar.getInstance()
        var dates: List<String> = date.split("-")
        var year: Int = Integer.parseInt(dates[0])
        var monthNum: Int = Integer.parseInt(dates[1])
        var day: Int = Integer.parseInt(dates[2])
        calendar.set(year, monthNum - 1, day)
        var weekNum: Int = calendar.get(Calendar.WEEK_OF_MONTH)
        var week = ""
        when (weekNum) {
            1 -> week = "1st"
            2 -> week = "2nd"
            3 -> week = "3rd"
            else -> week = weekNum.toString().plus("th")
        }
        var month = ""
        when (monthNum) {
            1 -> month = "January"
            2 -> month = "February"
            3 -> month = "March"
            4 -> month = "April"
            5 -> month = "May"
            6 -> month = "June"
            7 -> month = "July"
            8 -> month = "August"
            9 -> month = "September"
            10 -> month = "October"
            11 -> month = "November"
            12 -> month = "December"
        }

        return week.plus(" ").plus(month)
    }

    /**
     *  Top3 서버 통신
     */
    private val onTop3Success: (GetFeedTop3Response) -> Unit = {
        setTop3RecyclerView(it.data.topArr)
    }
    private val onTop3Failure: (Throwable) -> Unit = {
        Log.v("FeedFloodFragment", it.toString())
    }

    private fun getTop3Response() {
        networkService.getFeedTop3Response(token).safeEnqueue(onTop3Failure, onTop3Success)
    }

    /**
     * Top3  리사이클러뷰 설정
     */

    private fun setTop3RecyclerView(top3DataList: ArrayList<FeedTop3Data>) {
        feedTop3RVAdapter = FeedTop3RVAdapter(context!!, top3DataList)
        rv_feed_flood_top3.apply {
            adapter = feedTop3RVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
        feedTop3RVAdapter.notifyDataSetChanged()
        cl_feed_flood_loading.visibility = View.GONE
    }

    /**
     *  모든 게시물 조회  서버 통신
     */
    private fun getTodayResponse(page: Int, limit: Int) {
        networkService.getAllFeedResponse(token, page, limit).safeEnqueue({},
            onSuccess = {
                setTodayRecyclerView(it.data.pidArr)
            })
    }

    /**
     * Today 리사이클러뷰 설정
     */
    private fun setTodayRecyclerView(dataList: ArrayList<FeedData>) {
        feedRVAdapter = FeedRVAdapter(context!!, dataList)
        rv_feed_flood_today.apply {
            adapter = feedRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }

    /**
     *  Today 리사이클러뷰 페이징 처리하기
     */
    private fun setOnScrollChange() {
        nsv_feed_flood.setOnScrollChangeListener(object : View.OnScrollChangeListener {
            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
                if (!nsv_feed_flood.canScrollVertically(1)) {
                    val itemTotalCount: Int = rv_feed_flood_today.adapter!!.itemCount
                    networkService.getAllFeedResponse(token, 0, itemTotalCount + 5).safeEnqueue({},
                        onSuccess = {
                            Log.v("현주", "페이징 통신 성공")
                            feedRVAdapter.dataList = it.data.pidArr
                            feedRVAdapter.notifyDataSetChanged()
                            nsv_feed_flood.fullScroll(NestedScrollView.FOCUS_DOWN)
                        })
                }
            }
        })
    }

}