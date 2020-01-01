package com.flood_android.ui.signup.data

data class postSignupResponse (
    val image : String,
    val email : String,
    val password : String,
    val name : String,
    val phone : String,
    val rank : String,
    val checked : Boolean,
    val question : String,
    val answer : String
)
