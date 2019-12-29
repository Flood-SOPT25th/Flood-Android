package com.flood_android.network

import com.flood_android.ui.feed.data.GetPostBookmarkResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NetworkServiceUser {
    // 유저 북마크 리스트 조회
    @GET("/post/bookmark")
    fun getPostBookmarkResponse(
        @Header("Authorization") authorization : String
    ): Call<GetPostBookmarkResponse>
}