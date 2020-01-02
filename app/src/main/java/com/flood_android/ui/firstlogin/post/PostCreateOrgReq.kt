package com.flood_android.ui.firstlogin.post

data class PostCreateOrgReq (
    var name : String,
    var phone : String,
    var department : String,
    var category : List<String>
)