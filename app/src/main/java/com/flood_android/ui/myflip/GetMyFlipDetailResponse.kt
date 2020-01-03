package com.flood_android.ui.myflip

import com.flood_android.ui.feed.data.FeedData

data class GetMyFlipDetailResponse(
    val `data`: Data,
    val message: String
)

data class Data(
    val posts: ArrayList<FeedData>
)

data class Post(
    val _id: String,
    val _somethingElse: Int,
    val bookmark: Int,
    val category: String,
    val comments: List<String>,
    val comments_count: Int,
    val description: String,
    val groupCode: String,
    val image: String,
    val postContent: String,
    val postDate: String,
    val postImages: List<String>,
    val postTitle: String,
    val score: Double,
    val see: Int,
    val title: String,
    val url: String,
    val writer: String
)