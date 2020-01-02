package com.flood_android.ui.companydetail

data class GetCompanyDetailResponse(
    val data: GroupData,
    val message: String
)

data class GroupData(
    val groupInfo: GroupInfo
)

data class GroupInfo(
    val __v: Int,
    val _id: String,
    val category: List<String>,
    val department: String,
    val groupIcon: String,
    val groupCode: String,
    val groupImage: String,
    val name: String,
    val phone: String
)