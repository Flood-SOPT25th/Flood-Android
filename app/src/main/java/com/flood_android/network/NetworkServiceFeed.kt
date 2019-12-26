package com.flood_android.network

import com.flood_android.ui.feed.data.GetFeedCategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface NetworkServiceFeed {
    @GET("/group/category")
    fun getFeedCategoryResponse(
    ): Call<GetFeedCategoryResponse>
}