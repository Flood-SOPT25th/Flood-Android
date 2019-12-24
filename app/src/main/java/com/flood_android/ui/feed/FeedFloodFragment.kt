package com.flood_android.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.ui.feed.adapter.FeedRVAdapter
import com.flood_android.ui.feed.adapter.FeedTop3RVAdapter
import com.flood_android.ui.feed.data.FeedData
import com.flood_android.ui.feed.data.FeedTop3Data
import com.flood_android.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_feed_flood.*

class FeedFloodFragment : Fragment() {
    var top3DataList : ArrayList<FeedTop3Data> = ArrayList()
    var todayDataList : ArrayList<FeedData> = ArrayList()
    lateinit var feedRVAdapter : FeedRVAdapter
    lateinit var feedTop3RVAdapter: FeedTop3RVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_flood, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Feed 탭 처음에 flood 카테고리 화면을 띄우도록
        val transaction: FragmentTransaction = (context as MainActivity).supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_feed_fragment_frag, FeedFloodFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //Flood Top3 서버 통신
    private fun getTop3Response(){

    }

    //Flood Today 서버 통신
    private fun getTodayResponse(){

    }

    // Top3 리사이클러뷰
    private fun setTop3RecyclerView(){
        rv_feed_flood_top3.apply{
            adapter = FeedTop3RVAdapter(context!!, top3DataList)
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }

    /**
     *  페이징 처리하기!!!!!!!!!!!!!!!!!!!1
     */
    // Today 리사이클러뷰
    private fun setTodayRecyclerView(){
        rv_feed_flood_today.apply {
            adapter = FeedRVAdapter(context!!, todayDataList)
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        }
    }

}