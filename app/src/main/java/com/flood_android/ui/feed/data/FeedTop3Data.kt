package com.flood_android.ui.feed.data

data class FeedTop3Data(
  var news_title : String,
  var comments_num: Int,
  var flips_num: Int,
  var news_img : String,
  var news_url : String,
  var user_img: String,
  var user_name : String,
  var time : String,
  var contents: String
)