package com.flood_android.ui.feed

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.flood_android.R
import com.flood_android.ui.feed.adapter.FeedDetailCommentRVAdapter
import com.flood_android.ui.feed.data.FeedDetailCommentData
import com.flood_android.util.OnSingleClickListener
import kotlinx.android.synthetic.main.activity_feed_detail.*

class FeedDetailActivity : AppCompatActivity() {
    var commentDataList: ArrayList<FeedDetailCommentData> = ArrayList()
    lateinit var feedDetailCommentRVAdapter : FeedDetailCommentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_detail)

        initView()
    }

    private fun initView() {
        setOnClickListener()
        setCommentRecyclerView()
        activateComment()
    }

    private fun setOnClickListener() {
        // 댓글 업로드 버튼이 활성화되어있을 때만 업로드하기
        btn_feed_detail_upload_comment.setOnClickListener {
            (object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    if (btn_feed_detail_upload_comment.isSelected)
                        postComment()
                    else
                        Toast.makeText(this@FeedDetailActivity, "글자를 입력해주세요!", Toast.LENGTH_SHORT).show()
                }
            })
        }


    }


    // 댓글 리사이클러뷰 설정
    private fun setCommentRecyclerView(){
        feedDetailCommentRVAdapter = FeedDetailCommentRVAdapter(this@FeedDetailActivity, commentDataList)
        rv_feed_detail_comments.apply {
            adapter = feedDetailCommentRVAdapter
            layoutManager = LinearLayoutManager(this@FeedDetailActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    // 댓글창 활성화시키기
    private fun activateComment() {
        edt_feed_detail_commment_upload.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // 글자가 있을 때
                (p0.toString().trim().isNotEmpty()).apply {
                    btn_feed_detail_upload_comment.isSelected = true
                }
                // 글자가 없을 때
                (p0.toString().trim().isEmpty()).apply {
                    btn_feed_detail_upload_comment.isSelected = false
                }
            }
        })
    }

    // 댓글 게시 서버 통신
    private fun postComment(){

    }
}
