package com.flood_android.ui.mypage.data


data class GetMyPageResponse (
    val message : String,
    val data : UserInfo
)

data class UserInfo(
    var name : String,
    var profileImage : String,
    var rank : String,
    var groupName : String,
    var groupDepartment : String,
    var count : Int
)