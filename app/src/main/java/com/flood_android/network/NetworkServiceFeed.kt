package com.flood_android.network

import com.flood_android.ui.feed.data.GetFeedCategoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkServiceFeed {
    @GET("/group/category")
    fun getFeedCategoryResponse(
    ): Call<GetFeedCategoryResponse>
/*
    @Multipart
    @POST("/post")
    fun postPostResponse(
        @Header("token") token: String,
        @Part images: MultipartBody.Part?,
        @Part("url") url: RequestBody,
        @Part("category") category: RequestBody,
        @Part("content") content: RequestBody
    ): Call<PostPostResponse>

 */
}