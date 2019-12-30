package com.c.loginflood

data class PostLoginResponse(
    val `data`: PostLoginData,
    val message: String
)

data class PostLoginData(
    val refreshToken: String,
    val token: String
)