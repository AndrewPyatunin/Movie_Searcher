package com.andreich.moviesearcher.data.network

import android.util.Log
import com.andreich.moviesearcher.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    private fun readApiKeyFromFile(): String {
        return BuildConfig.API_KEY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = readApiKeyFromFile()
        Log.d("INTERCEPT", apiKey)
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("X-API-KEY", apiKey)
            .build()

        return chain.proceed(newRequest)
    }
}