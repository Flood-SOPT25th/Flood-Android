package com.flood_android.ui.feed.data

data class FeedData (
    var category : String,
    var user_img : String,
    var user_name : String,
    var time : String,
    var contents : String,
    var pic_list : ArrayList<String>,
    var news_title : String,
    var news_contents : String,
    var flips_num : Int,
    var comments_num : Int
)