package com.flood_android.ui.feed


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
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
import com.flood_android.ui.feed.data.GetFeedCategoryResponse
import com.flood_android.ui.main.MainActivity
import com.flood_android.ui.post.PostActivity
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.SharedPreferenceController
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_feed_detail.*
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed_flood.*
import kotlinx.android.synthetic.main.toast_feed_save_flips_category.*
import java.text.ParseException
import java.text.SimpleDateFormat


class FeedFragment : Fragment() {

    lateinit var getCategoryDataList: ArrayList<String>
    val networkService: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }
    var token = ""
    var postTime: Long? = null

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

        /**
         *  게시물이 있을 때 없을 때 구분하기~~~~~~~~~~~~~~~~~
         */
        setInvisible(cl_feed_no_news) // 게시물이 없는 화면 안보이게
    }

    private fun initView() {
        token = SharedPreferenceController.getAuthorization(context!!)!!
        Log.v("**현주", token)
        adapter = FeedCategoryRVAdapter(context!!) { position ->
            showFragment(feedFragments[position])
        }
        rv_feed_category_news.apply {
            adapter = this@FeedFragment.adapter
            layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        }
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

    private val floodFragment = FeedFloodFragment()
    private var feedFragments = listOf<Fragment>()
        set(value) {
            if (value.isEmpty()) return
            active = null
            value.forEach {
                addFragment(it)
            }
            showFragment(value.first())
            field = value
        }
    private var active: Fragment? = null
    private lateinit var adapter: FeedCategoryRVAdapter

    /**
     *  피드 카테고리 서버 통신
     */
    private var successGetCategory : (GetFeedCategoryResponse)  -> Unit  = { res ->
        feedFragments = listOf(floodFragment) + res.data.category.filterNot { it == "Flood" || it == "flood"}.map { FeedCategoryFragment(it) }

        adapter.dataList = res.data.category
        adapter.notifyDataSetChanged()
    }

    private fun getCategoryResponse() {
        networkService.getFeedCategoryResponse(token).safeEnqueue({}, successGetCategory)
    }

    /**
     * 북마크 추가 성공 토스트 띄우기
     */

    fun makeToast(ctx : Context, category_name: String, category_img : String) {
        val inflater: LayoutInflater = layoutInflater
        val toastDesign = inflater.inflate(
            R.layout.toast_feed_save_flips_category,
            view!!.findViewById(R.id.cl_feed_save_flips_category)
        )

        tv_feed_save_flips_category.text = category_name
        Glide.with(ctx)
            .load(category_img)
            .transform(CenterCrop(), RoundedCorners(40))
            .into(iv_feed_save_flips_category)


        val toast = Toast(ctx)
        toast.setGravity(Gravity.BOTTOM, 0, 120)  //center를 기준으로 0 0 위치에 메시지 출력
        toast.duration = Toast.LENGTH_SHORT
        toast.view = toastDesign

        toast.show()
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun setInvisible(view: View) {
        view.visibility = View.INVISIBLE
    }

    fun addFragment(fragment: Fragment){
        activity?.supportFragmentManager?.let { fm ->
            val transaction = fm.beginTransaction()
            transaction.add(R.id.fl_feed_fragment_frag, fragment).hide(fragment)
            transaction.commit()
        }
    }

    fun showFragment(fragment: Fragment){
        activity?.supportFragmentManager?.let { fm ->
            if (active == null) active = fragment

            val transaction = fm.beginTransaction()
            transaction.hide(active!!).show( fragment)
            transaction.commit()
            active = fragment
        }
    }
}
