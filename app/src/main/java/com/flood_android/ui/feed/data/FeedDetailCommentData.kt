package com.flood_android.ui.feed.data

data class FeedDetailCommentData (
    var user_img : String,
    var user_name : String,
    var contents : String,
    var time : String,
    var recomment_list : ArrayList<FeedDetailRecommentData>
)

data class FeedDetailRecommentData(
    var user_img : String,
    var user_name : String,
    var contents : String,
    var time : String
)