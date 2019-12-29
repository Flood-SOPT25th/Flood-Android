package com.flood_android.ui.feed

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.ui.feed.adapter.FeedDetailCommentRVAdapter
import com.flood_android.ui.feed.data.FeedDetailCommentData
import com.flood_android.ui.main.MainActivity
import com.flood_android.util.OnSingleClickListener
import kotlinx.android.synthetic.main.activity_feed_detail.*


class FeedDetailActivity : AppCompatActivity() {
    var commentDataList: ArrayList<FeedDetailCommentData> = ArrayList()
    lateinit var feedDetailCommentRVAdapter: FeedDetailCommentRVAdapter
    lateinit var feedIdx : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_detail)

        initView()
    }

    private fun initView() {
        getVariables()
        setOnClickListener()
        setCommentRecyclerView()
        activateComment()
    }

    private fun getVariables() : String {
        setResult(Activity.RESULT_OK, intent)
        feedIdx = intent.getStringExtra("feed_id")
        return feedIdx
    }

    /**
     * 게시물 조회 서버 통신
     */
    private fun getFeedDetailResponse(){

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
            (object  : OnSingleClickListener(){
                override fun onSingleClick(v: View) {
                    val intent = Intent(this@FeedDetailActivity, WebViewActivity::class.java)
                    // intent.putExtra("url", )
                   this@FeedDetailActivity. startActivity(intent)
                }
            })
        }

        btn_feed_detail_flips.setOnClickListener (object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                postComment()
            }
        })

        btn_feed_detail_back.setOnClickListener (object : OnSingleClickListener(){
            override fun onSingleClick(v: View) {
                finish()
            }
        })
    }

    private fun focusKeyboard() {
        edt_feed_detail_comment_upload.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    // 댓글 리사이클러뷰 설정
    private fun setCommentRecyclerView() {
        feedDetailCommentRVAdapter =
            FeedDetailCommentRVAdapter(this@FeedDetailActivity, commentDataList)
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
                if (p0.toString().trim().isNotEmpty()){
                    btn_feed_detail_upload_comment.isSelected = true
                }
                else {
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
        val ivFlips  = findViewById(R.id.iv_feed_detail_flips) as ImageView
        if (ivFlips.isSelected)      //북마크 취소
            ivFlips.isSelected = false
        else{   // 북마크하기
            (applicationContext as MainActivity).makeFlipDialog(ivFlips)
        }
    }

    //
    private fun setFlips(){

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}