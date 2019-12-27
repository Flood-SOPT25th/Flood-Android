package com.flood_android.network

import com.flood_android.ui.feed.data.GetFeedCategoryResponse
import com.flood_android.ui.post.PostPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.*

interface NetworkServiceFeed {
    @GET("group/category")
    fun getFeedCategoryResponse(
        @Header("Authorization") autrhorization : String
    ): Call<GetFeedCategoryResponse>

    @Multipart
    @POST("post")
    fun postPostResponse(
        @Header("Authorization") token: String,
        @Part images: ArrayList<MultipartBody.Part>?,
        @Part("url") url: RequestBody,
        @Part("category") category: RequestBody,
        @Part("postContent") content: RequestBody
    ): Call<PostPostResponse>
}