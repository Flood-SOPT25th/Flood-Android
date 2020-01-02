package com.flood_android.ui.feed.data

import com.google.gson.annotations.SerializedName

data class GetFeedDetailResponse(
    val message : String,
    val data : FeedDetailDataResponse
)

data class FeedDetailDataResponse(
    val pidArr : FeedDetailData
)

data class FeedDetailData(
    val postImages : ArrayList<String>,

    @SerializedName("bookmark")
    var bookmark_cnt : Int,

    @SerializedName("comments_count")
    var comment_cnt : Int,

    @SerializedName("postDate")
    var time : String,

    val _id : String,

    val category : String,

    @SerializedName("image")
    val news_img: String,

    @SerializedName("title")
    val news_title : String,

    @SerializedName("description")
    val news_content : String,

    val writer : String,

    @SerializedName("profileImage")
    val post_user_img : String,

    @SerializedName("postContent")
    val post_content : String,

    @SerializedName("url")
    val news_url : String,

    val bookmarked : Boolean,

    val comments : ArrayList<CommentsData>?
)


data class CommentsData(
    @SerializedName("comment_date")
    val comment_time : String,

    @SerializedName("_id")
    val comment_id : String,

    @SerializedName("content")
    val comment_content : String,

    @SerializedName("writer")
    val comment_user_name : String,

    @SerializedName("profileImage")
    val comment_user_img : String,

    val subComment : ArrayList<SubCommentData>?
)

data class SubCommentData(
    @SerializedName("_id")
    val subcomment_id : String,

    @SerializedName("content")
    val subcomment_content : String,

    @SerializedName("writer")
    val subcomment_user_name : String,

    @SerializedName("profileImage")
    val subcomment_user_img : String,

    @SerializedName("comment_date")
    var time : String
)