package com.flood_android.network

import com.c.loginflood.PostLoginRequest
import com.c.loginflood.PostLoginResponse
import com.flood_android.ui.feed.data.GetPostBookmarkResponse
import com.flood_android.ui.mypage.data.GetMyPageResponse
import com.flood_android.ui.signup.data.PostSignupRequest
import com.flood_android.ui.signup.data.PostSignupResponse
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
     * 회원가입
     */
    @POST("/auth/signup")
    fun postSignupResponse(
        @Body body : PostSignupRequest
    ): Call<PostSignupResponse>

    /**
     * 마이페이지
     */
    @GET("/mypage/main")
    fun getMyPageResponse(
        @Header("Authorization") authorization : String
    ) : Call<GetMyPageResponse>
}