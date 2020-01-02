package com.flood_android.network

import com.flood_android.ui.companydetail.GetCompanyDetailFeedResponse
import com.flood_android.ui.companydetail.GetCompanyDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface NetworkServiceCompany {
    /**
     * 회사 피드 GET
     */
    @GET("/group/search/{groupCode}")
    fun getCompanyDetailResponse(
        @Header("Authorization") authorization: String,
        @Path("groupCode") groupCode : String
    ) : Call<GetCompanyDetailResponse>

    @GET("/group/search/{groupCode}/{category}")
    fun getCompanyDetailFeedResponse(
        @Header("Authorization") authorization: String,
        @Path("groupCode") groupCode : String,
        @Path("category") category : String
    ) : Call<GetCompanyDetailFeedResponse>
}