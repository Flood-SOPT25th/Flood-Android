package com.flood_android.network

import com.c.loginflood.PostLoginRequest
import com.c.loginflood.PostLoginResponse
import com.flood_android.ui.bookmarkedit.post.PostFlipRequest
import com.flood_android.ui.bookmarkedit.post.PostFlipResponse
import com.flood_android.ui.feed.data.GetPostBookmarkResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkServiceUser {
    // 유저 북마크 리스트 조회
    @GET("/post/bookmark")
    fun getPostBookmarkResponse(
        @Header("Authorization") authorization : String
    ): Call<GetPostBookmarkResponse>

    /**
     * 로그인
     */
    @POST("/auth/signin")
    fun postLoginResponse(
        @Header("Content-Type") content_type: String,
        @Body body: PostLoginRequest
    ): Call<PostLoginResponse>

    /**
     * 플립 편집
     */
    @POST("/post/bookmark")
    fun postFlipResponse(
        @Header("Content-Type") content_type: String,
        @Header("Authorization") token: String,
        @Body body: PostFlipRequest
    ): Call<PostFlipResponse>
}