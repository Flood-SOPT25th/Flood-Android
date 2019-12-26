package com.flood_android.ui.feed.data

data class GetFeedCategoryResponse (
    var message : String,
    var data : FeedCategoryData
)

data class FeedCategoryData(
    var category : ArrayList<String>
)