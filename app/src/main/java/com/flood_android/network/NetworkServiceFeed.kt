package com.flood_android.network

import com.flood_android.ui.company.GetCompanyResponse
import com.flood_android.ui.feed.data.*
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

    // 모든 게시물 조회
    @GET("/post")
    fun getAllFeedResponse(
        @Header("Authorization") authorization: String
    ):Call<GetAllFeedResponse>

    // 카테고리별 게시물 조회
    @GET("/post/hash")
    fun getCategoryFeedResponse(
        @Header("Authorization") authorization: String,
        @Query("category") category : String
    ) : Call<GetCategoryFeedResponse>

    // 게시물 상세 조회
    @GET("/post/detail/{idx}")
    fun getFeedDetailResponse(
        @Header("Authorization") authorization: String,
        @Path("idx") idx : String
    ) : Call<GetFeedDetailResponse>

    // 북마크 추가
    @POST("post/bookmark/add")
    fun postBookmarkAddRequest(
        @Header("Authorization") authorization: String,
        @Body body : PostBookmarkAddData
    ): Call<PostBookmarkAddData>

    // 북마크 취소
    @POST("/post/bookmark/cancel")
    fun postBookmarkCancelRequest(
        @Header("Authorization") authorization: String,
        @Body body: PostBookmarkCancelData
    ) : Call<PostBookmarkCancelData>

    // 댓글 / 대댓글 달기
    @POST("/comment")
    fun postCommentRequest(
        @Header("Authorization") authorization: String,
        @Body body : PostCommentData
    ):Call<PostCommentData>

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
    @GET("/group/category")
    fun getPostResponse(
        @Header("Authorization") token: String
    ): Call<GetPostResponse>

    /**
     * 회사 피드 GET
     */
    @GET("/group/search")
    fun getCompanyResponse(
        @Header("Authorization") token: String
    ): Call<GetCompanyResponse>

    /**
     *  내가 쓴 글 조회
     */
    @GET("/post/me")
    fun getMyPostResponse(
        @Header("Authorization") authorization: String
    ): Call<GetAllFeedResponse>
}