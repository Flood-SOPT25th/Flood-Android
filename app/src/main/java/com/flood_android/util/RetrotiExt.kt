package com.flood_android.util

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.safeEnqueue(
    onFailure: (Throwable) -> Unit = {},
    onSuccess: (T) -> Unit = {}
) {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            Log.v("Postygyg", response.message().toString())
            Log.v("Postygyg", response.toString())

            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess(it)
                } ?: Log.e("retrofitExt", "error")
            } else{
                Log.v("Postygyg", "fail")
            }
        }
    })
}