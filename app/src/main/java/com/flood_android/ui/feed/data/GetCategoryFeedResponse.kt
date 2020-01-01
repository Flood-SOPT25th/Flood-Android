package com.flood_android.ui.feed.data

data class GetCategoryFeedResponse(
    val message : String,
    var data : CategoryFeedResponse
)

data class CategoryFeedResponse(
    val pidArr : ArrayList<FeedData>
)