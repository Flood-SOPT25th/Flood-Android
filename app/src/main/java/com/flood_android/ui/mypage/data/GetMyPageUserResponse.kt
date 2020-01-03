package com.flood_android.ui.mypage.data


data class GetMyPageUserResponse (
    val message : String,
    val data : MypageUserData
)

data class MypageUserData(
        val userInfo : UserInfo
)

data class UserInfo(
    var name : String,
    var profileImage : String,
    var rank : String,
    var groupName : String,
    var groupDepartment : String,
    var count : Int
)