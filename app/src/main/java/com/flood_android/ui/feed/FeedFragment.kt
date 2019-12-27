package com.flood_android.ui.feed


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.ui.feed.adapter.FeedCategoryRVAdapter
import com.flood_android.ui.feed.adapter.FeedSaveFlipsCategoryRVAdapter
import com.flood_android.ui.feed.data.FeedSaveFlipsCategoryData
import com.flood_android.ui.feed.data.GetFeedCategoryResponse
import com.flood_android.ui.main.MainActivity
import com.flood_android.ui.post.PostActivity
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.safeEnqueue
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.Holder
import com.orhanobut.dialogplus.ViewHolder
import kotlinx.android.synthetic.main.dialog_feed_save_flips.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.toast_feed_save_flips_category.*
import java.text.ParseException
import java.text.SimpleDateFormat


class FeedFragment : Fragment() {

    lateinit var flipsCategoryDataList: ArrayList<FeedSaveFlipsCategoryData>
    lateinit var dialog: DialogPlus

    lateinit var getCategoryDataList: ArrayList<String>
    val networkService: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }

    var postTime: Long? = null
    var token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 화면 초기화
        initView()

//        Log.d("현주", calculateTime("2019-12-23T10:22:52.915Z"))

        //Feed 탭 처음에 flood 카테고리 화면을 띄우도록

        /**
         *  게시물이 있을 때 없을 때 구분하기~~~~~~~~~~~~~~~~~
         */
        setInvisible(cl_feed_no_news) // 게시물이 없는 화면 안보이게


        val transaction: FragmentTransaction =
            (context as MainActivity).supportFragmentManager.beginTransaction()
        transaction.add(R.id.fl_feed_fragment_frag, FeedFloodFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun initView() {
        getCategoryResponse()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        btn_feed_go_post.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                val intent = Intent(context!!, PostActivity::class.java)
                this@FeedFragment.startActivity(intent)
            }
        })
    }

    /**
     *  피드 카테고리 서버 통신
     */
    private var successGetCategory : (GetFeedCategoryResponse)  -> Unit  = {
        setCategoryRecyclerView(it.data.category)
    }

    private fun getCategoryResponse() {
        networkService.getFeedCategoryResponse(token).safeEnqueue({}, successGetCategory)
    }


    /**
     *  플립에 저장 완료 시 토스트 띄우기
     */
    fun makeToast(img_url: String, category: String) {
        var inflater: LayoutInflater = layoutInflater
        val toastDesign = inflater.inflate(
            R.layout.toast_feed_save_flips_category,
            (R.id.cl_feed_save_flips_category) as ViewGroup
        )

        Glide.with(context!!)
            .load(img_url)
            .transform(CenterCrop(), RoundedCorners(10))
            .into(iv_feed_save_flips_category)
        tv_feed_save_flips_category.text = category

        var toast = Toast(context)
        toast.setGravity(Gravity.CENTER, 0, 0)  //center를 기준으로 0 0 위치에 메시지 출력
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastDesign
        toast.show()
    }


    /**
     *  플립에 저장하기 다이얼로그 띄우기
     */
    fun makeFlipDialog(ivSelector: ImageView) {
        ivSelector.isSelected = true

        val holder: Holder = ViewHolder(R.layout.dialog_feed_save_flips)

        setFlipCategoryRecyclerView(flipsCategoryDataList)

        dialog = DialogPlus.newDialog(context)
            .apply {
                setContentHolder(holder)
                setGravity(Gravity.BOTTOM)
                setCancelable(true)
                setExpanded(true)
            }
            .setOnCancelListener {
                ivSelector.isSelected = false
            }
            .setOnBackPressListener {
                ivSelector.isSelected = false
            }
            .create()

        dialog.show()
    }

    fun dismissFlipDialog() {
        dialog.dismiss()
    }

    /**
     *  피드 카테고리 리사이클러뷰 설정
     */
    private fun setCategoryRecyclerView(dataList: ArrayList<String>) {
        var feedCategoryRVAdapter = FeedCategoryRVAdapter(context!!, dataList)
        rv_feed_category_news.apply {
            adapter = feedCategoryRVAdapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    /**
     *  플립 카테고리 리사이클러뷰 설정
     */
    private fun setFlipCategoryRecyclerView(dataList: ArrayList<FeedSaveFlipsCategoryData>) {
        var feedSaveFlipsCategoryRVAdapter = FeedSaveFlipsCategoryRVAdapter(context!!, dataList)
        rv_dialog_feed_save_flips_category.apply {
            adapter = feedSaveFlipsCategoryRVAdapter
            layoutManager =
                LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        }
        feedSaveFlipsCategoryRVAdapter.notifyDataSetChanged()
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun setInvisible(view: View) {
        view.visibility = View.INVISIBLE
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

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var formattedPostTime = dateFormat.format(formattedServerTime)

        postTime?: try {
            postTime = dateFormat.parse(formattedPostTime).time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        Log.d("현주 시간 계산", postTime.toString())

        var curTime: Long = System.currentTimeMillis()

        var diff: Long = curTime - postTime!!

        var gapMin: Long = diff / 60000
        var gapHour: Long = gapMin / 60
        var gapDay: Long = gapHour / 24
        var gapWeek: Long = gapDay / 7
        var gapMonth: Long = gapDay / 30
        var gapYear: Long = gapDay / 365

        Log.d("현주", gapYear.toString())

        if (gapYear >= 1) {
            return gapYear.toInt().toString() + "년 전"
        } else {
            if (gapMonth >= 1) {
                return gapMonth.toInt().toString() + "달 전"
            } else {
                if (gapWeek >= 1) {
                    return gapWeek.toInt().toString() + "주 전"
                } else {
                    if (gapDay >= 1) {
                        return gapDay.toInt().toString() + "일 전"
                    } else {
                        if (gapHour >= 1) {
                            return gapHour.toInt().toString() + "시간 전"
                        } else {
                            return gapMin.toInt().toString() + "분 전"
                        }
                    }
                }
            }
        }

    }
}
