package com.flood_android.ui.feed.data

data class GetPostBookmarkResponse(
    val message : String,
    val data : BookmarkListData
)

data class BookmarkListData(
    val categorys : ArrayList<BookmarkData>
)

data class BookmarkData(
    var category_id : String?,
    var categoryName : String,
    var thumb : String,
    var count : Int
)