package com.flood_android.ui.feed

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.flood_android.R
import com.flood_android.network.ApplicationController
import com.flood_android.network.NetworkServiceFeed
import com.flood_android.ui.feed.adapter.FeedDetailCommentRVAdapter
import com.flood_android.ui.feed.data.CommentsData
import com.flood_android.ui.feed.data.FeedDetailCommentData
import com.flood_android.ui.feed.data.GetFeedDetailResponse
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.OnSingleClickListener
import com.flood_android.util.safeEnqueue
import kotlinx.android.synthetic.main.activity_feed_detail.*


class FeedDetailActivity : AppCompatActivity() {
    var imgList = ArrayList<String>()

    private val flipsSaveDialog by lazy{
        FeedFlipsSaveDialog()
    }

    lateinit var feedDetailCommentRVAdapter: FeedDetailCommentRVAdapter
    private val networkServiceFeed: NetworkServiceFeed by lazy {
        ApplicationController.networkServiceFeed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_detail)

        initView()
    }

    private fun initView() {
        var feedIdx: String = intent.getStringExtra("feed_id")
        getFeedDetailResponse(feedIdx)
        setOnClickListener()
        activateComment()
    }

    /**
     * 게시물 조회 서버 통신
     */
    private val onGetSuccess : (GetFeedDetailResponse) -> Unit = {response->

        response.data.pidArr.let{
            tv_feed_detail_flips_cnt.text = it.bookmark_cnt.toString()
            tv_feed_detail_comments_cnt.text = it.comment_cnt.toString()
            tv_feed_detail_time.text = it.time
            tv_feed_detail_category.text = it.category
            tv_feed_detail_user_contents.text = it.post_content

            imgList = it.postImages

            // 사진이 있을 때 사진 나타내기
            var pic_num: Int? = it.postImages.size

            if (pic_num == 0){
                imgList = it.postImages
            }

            when (pic_num!!) {
                0 -> setGone(cv_feed_detail_img)
                1 -> {
                    setVisible(iv_feed_detail_pic_1)
                    setInvisible(ll_feed_detail_pic_2)
                    setInvisible(cl_feed_detail_pic_3)

                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[0])
                        .centerCrop()
                        .into(iv_feed_detail_pic_1)
                }
                2 -> {
                    setInvisible(iv_feed_detail_pic_1)
                    setVisible(ll_feed_detail_pic_2)
                    setInvisible(cl_feed_detail_pic_3)

                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[0])
                        .centerCrop()
                        .into(iv_feed_detail_pic_2_1)
                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[1])
                        .centerCrop()
                        .into(iv_feed_detail_pic_2_2)
                }
                3 -> {
                    setInvisible(iv_feed_detail_pic_1)
                    setInvisible(ll_feed_detail_pic_2)
                    setVisible(cl_feed_detail_pic_3)

                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[0])
                        .centerCrop()
                        .into(iv_feed_detail_pic_3_1)
                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[1])
                        .centerCrop()
                        .into(iv_feed_detail_pic_3_2)
                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[2])
                        .centerCrop()
                        .into(iv_feed_detail_pic_3_3)
                }
                //4개 이상
                else -> {
                    var etc_num = pic_num - 3
                    tv_feed_detail_pic_3_3_etc.text = "+" + etc_num.toString()
                    setVisible(cl_feed_detail_pic_3_3_black)
                    setVisible(tv_feed_detail_pic_3_3_etc)

                    setInvisible(iv_feed_detail_pic_1)
                    setInvisible(ll_feed_detail_pic_2)
                    setVisible(cl_feed_detail_pic_3)

                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[0])
                        .centerCrop()
                        .into(iv_feed_detail_pic_3_1)
                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[1])
                        .centerCrop()
                        .into(iv_feed_detail_pic_3_2)
                    Glide.with(this@FeedDetailActivity)
                        .load(it.postImages[2])
                        .centerCrop()
                        .into(iv_feed_detail_pic_3_3)
                }
            }

            if (it.news_url == ""){
                setGone(cl_feed_detail_news)
            }else{
                Glide.with(this@FeedDetailActivity)
                    .load(it.news_img)
                    .transform(CenterCrop(), RoundedCorners(10))
                    .into(iv_feed_detail_news_img)
                tv_feed_detail_news_title.text = it.news_title

                if (it.news_content == ""){
                    setGone(tv_feed_detail_news_contents)
                }else{
                    tv_feed_detail_news_contents.text = it.news_content
                }
                tv_feed_detail_user_name.text = it.writer
            }

            Glide.with(this@FeedDetailActivity)
                .load(it.post_user_img)
                .transform(CenterCrop(), CircleCrop())
                .into(iv_feed_detail_user_img)

            iv_feed_detail_flips.isSelected = it.bookmarked

            cl_feed_detail_news.setOnClickListener(object : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.news_url))
                    this@FeedDetailActivity.startActivity(intent)
                }
            })
            if (it.comments != null){
                setCommentRecyclerView(it.comments)
            }
        }
    }

    private fun getFeedDetailResponse(feed_idx: String) {
        var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImVoZGduczE3NjZAZ21haWwuY29tIiwibmFtZSI6IuydtOuPme2biCIsImlhdCI6MTU3NzQwNzg1NiwiZXhwIjoxNTc5OTk5ODU2LCJpc3MiOiJGbG9vZFNlcnZlciJ9.Zf_LNfQIEdFl84r-tPQpT1nLaxdotkFutOxwNQy-w58"
        networkServiceFeed.getFeedDetailResponse(token, feed_idx).safeEnqueue({}, onGetSuccess )
    }

    private fun setOnClickListener() {
        // 댓글 업로드 버튼이 활성화되어있을 때만 업로드하기
        btn_feed_detail_upload_comment.setOnClickListener {
            (object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (btn_feed_detail_upload_comment.isSelected)
                        postComment()
                    else
                        Toast.makeText(
                            this@FeedDetailActivity,
                            "글자를 입력해주세요!",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            })
        }

        // 댓글창 클릭하면 키보드에 포커스
        cl_feed_detail_comment_upload.setOnClickListener {
            (object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    focusKeyboard()
                }
            })
        }

        // editText 클릭하면 키보드에 포커스
        edt_feed_detail_comment_upload.setOnClickListener {
            (object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    focusKeyboard()
                }
            })
        }

        // 뉴스 기사 눌렀을 때 웹뷰로 이동
        cl_feed_detail_news.setOnClickListener {
            (object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    val intent = Intent(this@FeedDetailActivity, WebViewActivity::class.java)
                    this@FeedDetailActivity.startActivity(intent)
                }
            })
        }

        btn_feed_detail_flips.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                postComment()
            }
        })

        // 뒤로 가기 눌렀을 때
        btn_feed_detail_back.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                finish()
            }
        })

        // 이미지 눌렀을 때
        cv_feed_detail_img.setOnClickListener (object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                openPhotoZoomActivity()
            }
        })
    }

    private fun focusKeyboard() {
        edt_feed_detail_comment_upload.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 포토줌액티비티 열기
     */
    private fun openPhotoZoomActivity(){
        val intent = Intent(this@FeedDetailActivity, PhotoZoomActivity::class.java)
        intent.putStringArrayListExtra("imageList", imgList)
        this.startActivity(intent)


    }

    /**
     * 댓글 리사이클러뷰 설정
     */
    private fun setCommentRecyclerView(dataList : ArrayList<CommentsData>) {
        rv_feed_detail_comments.isNestedScrollingEnabled = false
        rv_feed_detail_comments.setHasFixedSize(false)

        feedDetailCommentRVAdapter =
            FeedDetailCommentRVAdapter(this@FeedDetailActivity, dataList)
        rv_feed_detail_comments.apply {
            adapter = feedDetailCommentRVAdapter
            layoutManager =
                LinearLayoutManager(this@FeedDetailActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    // 댓글창 활성화시키기
    private fun activateComment() {
        edt_feed_detail_comment_upload.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 글자가 있을 때
                if (p0.toString().trim().isNotEmpty()) {
                    btn_feed_detail_upload_comment.isSelected = true
                } else {
                    btn_feed_detail_upload_comment.isSelected = false
                }
            }
        })
    }

    // 답글 달기
    fun recomment(username: String) {
        var tagUser: String = ("@").plus(username).plus(" ")
        edt_feed_detail_comment_upload.setTypeface(null, Typeface.BOLD)    //tagUser만 진하게 하려고
        edt_feed_detail_comment_upload.setText(tagUser)
        edt_feed_detail_comment_upload.setTypeface(null, Typeface.NORMAL)
        edt_feed_detail_comment_upload.setSelection(edt_feed_detail_comment_upload.length())
    }

    // 댓글 게시 서버 통신
    private fun postComment() {
        val ivFlips = findViewById(R.id.iv_feed_detail_flips) as ImageView
        if (ivFlips.isSelected)      //북마크 취소
            ivFlips.isSelected = false
        else {   // 북마크하기
            //(applicationContext as MainActivity).makeFlipDialog(ivFlips)
            flipsSaveDialog.show(this@FeedDetailActivity.supportFragmentManager, "")
        }
    }

    //
    private fun setFlips() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setGone(view: View) {
        view.visibility = View.GONE
    }

    private fun setVisible(view: View) {
        view.visibility = View.VISIBLE
    }

    private fun setInvisible(view: View) {
        view.visibility = View.INVISIBLE
    }
}