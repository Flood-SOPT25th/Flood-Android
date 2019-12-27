package com.flood_android.network

import com.flood_android.ui.feed.data.GetFeedCategoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NetworkServiceFeed {
    @GET("/group/category")
    fun getFeedCategoryResponse(
        @Header("Authorization") autrhorization : String
    ): Call<GetFeedCategoryResponse>
}