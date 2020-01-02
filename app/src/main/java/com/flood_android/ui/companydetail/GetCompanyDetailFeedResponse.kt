package com.flood_android.ui.companydetail

import com.flood_android.ui.feed.data.FeedData

data class GetCompanyDetailFeedResponse(
    val `data`: Data,
    val message: String
)

data class Data(
    val groupArr: ArrayList<FeedData>
)

data class GroupArr(
    val _id: String,
    val _somethingElse: Int,
    val bookmark: Int,
    val bookmark_list: List<Any>,
    val category: String,
    val comments_count: Int,
    val description: String,
    val groupCode: String,
    val image: String,
    val postContent: String,
    val postDate: String,
    val postImages: List<String>,
    val postTitle: String,
    val profileImage: String,
    val score: Int,
    val see: Int,
    val title: String,
    val url: String,
    val writer: String,
    val writer_email: String
)