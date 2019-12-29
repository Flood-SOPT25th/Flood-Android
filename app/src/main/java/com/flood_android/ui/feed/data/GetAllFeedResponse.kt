package com.flood_android.ui.feed.data

import com.google.gson.annotations.SerializedName

data class GetAllFeedResponse(
    val message: String,
    var data : AllFeedResponse
)

data class AllFeedResponse(
    val pidArr : ArrayList<FeedData>
)

data class FeedData (
    var category : String,

    @SerializedName("profileImage")
    var user_img : String,

    @SerializedName("writer")
    var user_name : String,

    @SerializedName("postDate")
    var time : String,

    @SerializedName("postContent")
    var contents : String,

    @SerializedName("postImages")
    var pic_list : ArrayList<String>,

    @SerializedName("url")
    var news_url : String,

    @SerializedName("image")
    var news_img : String,

    @SerializedName("title")
    var news_title : String,

    @SerializedName("description")
    var news_contents : String,

    @SerializedName("bookmark")
    var flips_num : Int,

    @SerializedName("comments_count")
    var comments_num : Int,

    @SerializedName("bookmarked")
    var bookmark_flag : Boolean,

    var _id : String
)