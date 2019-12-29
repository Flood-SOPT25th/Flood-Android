package com.flood_android.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApplicationController {
    private val baseURL = "http://flooddocker-env.3gfczrrijh.ap-northeast-2.elasticbeanstalk.com"

    lateinit var networkServiceFeed: NetworkServiceFeed
    lateinit var networkServiceUser: NetworkServiceUser

    init{
        buildNetwork()
    }

    fun buildNetwork(){
    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

        networkServiceFeed = retrofit.create(NetworkServiceFeed::class.java)
        networkServiceUser = retrofit.create(NetworkServiceUser::class.java)
    }
}