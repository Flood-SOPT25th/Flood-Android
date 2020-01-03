package com.flood_android.ui.myprofile.get

data class GetMyProfileEditResponse(
    val `data`: GetMyProfileEditdata,
    val message: String
)

data class GetMyProfileEditdata(
    val group: Group,
    val user: User
)

data class Group(
    val name: String
)

data class User(
    val admin: Boolean,
    val email: String,
    val groupCode: String,
    val name: String,
    val phone: String,
    val profileImage: String,
    val rank: String
)