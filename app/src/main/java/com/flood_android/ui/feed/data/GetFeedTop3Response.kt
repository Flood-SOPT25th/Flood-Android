package com.flood_android.ui.feed.data

import com.google.gson.annotations.SerializedName

data class GetFeedTop3Response(
    val message: String,
    val data: FeedTop3Response

)

data class FeedTop3Response(
    val topArr: ArrayList<FeedTop3Data>
)

data class FeedTop3Data(
    @SerializedName("title")
    var news_title: String,

    @SerializedName("comments_count")
    var comments_num: Int,

    @SerializedName("bookmark")
    var flips_num: Int,

    @SerializedName("image")
    var news_img: String,

    @SerializedName("url")
    var news_url: String,

    @SerializedName("postImages")
    var post_img: ArrayList<String>,

    @SerializedName("profileImage")
    var user_img: String,

    @SerializedName("writer")
    var user_name: String,

    @SerializedName("postDate")
    var time: String,

    @SerializedName("postContent")
    var contents: String,

    @SerializedName("bookmarked")
    var flip_flag : Boolean,

    //var see: Int,

    @SerializedName("score")
    var score: Double,

//    @SerializedName("comments")
//    var comments: ArrayList<String>,

    var _id: String
)