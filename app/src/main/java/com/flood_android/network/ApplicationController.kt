package com.flood_android.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController: Application() {
    lateinit var networkServiceFeed: NetworkServiceFeed
    lateinit var networkServiceUser: NetworkServiceUser

    private val baseUrl = ""

    companion object {
        lateinit var instance : ApplicationController
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()
    }

    fun buildNetwork(){

        val builder = Retrofit.Builder()
        val retrofit = builder
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        networkServiceFeed = retrofit.create(NetworkServiceFeed::class.java)
        networkServiceUser = retrofit.create(NetworkServiceUser::class.java)
    }
}