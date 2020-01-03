package com.flood_android.ui.company

data class GetCompanyResponse(
    val data: GetCompanyData,
    val message: String
)

data class GetCompanyData(
    val groupArr: List<GroupArr>
)

data class GroupArr(
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