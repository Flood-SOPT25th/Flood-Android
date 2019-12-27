package com.flood_android.network

import com.flood_android.ui.feed.data.GetFeedCategoryResponse
import com.flood_android.ui.feed.data.GetFeedTop3Response
import com.flood_android.ui.post.PostPostResponse
import com.flood_android.ui.post.get.GetPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.*

interface NetworkServiceFeed {
    // 해당 조직의 카테고리
    @GET("/group/category")
    fun getFeedCategoryResponse(
        @Header("Authorization") autrhorization : String
    ): Call<GetFeedCategoryResponse>

    // Top3 피드 조회
    @GET("/post/top")
    fun getFeedTop3Response(
        @Header("Authorization") authorization: String
    ): Call<GetFeedTop3Response>

    /**
     * 게시물 등록 POST
     */
    @Multipart
    @POST("/post")
    fun postPostResponse(
        @Header("Authorization") token: String,
        @Part images: ArrayList<MultipartBody.Part>?,
        @Part("url") url: RequestBody,
        @Part("category") category: RequestBody,
        @Part("postContent") content: RequestBody
    ): Call<PostPostResponse>

    /**
     * 게시물 등록 GET
     */
    @GET("group/category")
    fun getPostResponse(
        @Header("Authorization") token: String
    ): Call<GetPostResponse>
}