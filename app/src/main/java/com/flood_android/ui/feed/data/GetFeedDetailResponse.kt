package com.flood_android.ui.feed.data

data class GetFeedDetailResponse(
    val message : String,
    val data : FeedDetailData
)

data class FeedDetailData(
    val postImages : ArrayList<String>


)