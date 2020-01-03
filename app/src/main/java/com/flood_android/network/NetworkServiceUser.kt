package com.flood_android.network

import com.c.loginflood.PostLoginRequest
import com.c.loginflood.PostLoginResponse
import com.flood_android.ui.bookmarkedit.post.PostFlipRequest
import com.flood_android.ui.bookmarkedit.post.PostFlipResponse
import com.flood_android.ui.feed.data.GetPostBookmarkResponse
import com.flood_android.ui.firstlogin.post.*
import com.flood_android.ui.mypage.data.GetMyPageMainResponse
import com.flood_android.ui.signup.data.PostSignupRequest
import com.flood_android.ui.signup.data.PostSignupResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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
     * 마이페이지 유저 정보
     */
    @GET("/mypage/main")
    fun getMyPageMainInfo(
        @Header("Authorization") authorization : String
    ) : Call<GetMyPageMainResponse>

    /**
     * 회원가입
     */
    @POST("/auth/signup")
    fun postSignupResponse(
        @Body body : PostSignupRequest
    ): Call<PostSignupResponse>

    /**
     * 조직생성
     */
    @POST("/auth/signup/organization")
    fun postCreateOrganization(
        @Header("Context-Type") context_type: String,
        @Header("Authorization") authorization: String,
        @Body body : PostCreateOrgReq
    ): Call<PostCreateOrgResponse>

    /**
     * 조직 가입
     */
    @POST("/auth/signin/organization")
    fun postSignInOrganization(
        @Header("Context-type") context_type:String,
        @Header("Authorization") authorization : String,
        @Body body : PostSignInOrgReq
    ): Call<PostSignInOrgResponse>

    /**
     * 조직가입 후 개인 프로필 설정 //TODO 확인
     */
    @POST("/auth/profile")
    fun postProfileSetting(
        @Header("Context-Type") context_type: String,
        @Header("Authorization") authorization: String,
        @Part("image") image: MultipartBody.Part?,
        @Part("profileName") profile_name: RequestBody,
        @Part("rank") profile_rank : RequestBody
    ): Call<PostProfileSetResponse>


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