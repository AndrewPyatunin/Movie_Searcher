package com.andreich.moviesearcher.data.network

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException

class ApiKeyInterceptor(private val context: Context) : Interceptor {

    private fun readApiKeyFromFile(): String {
        return try {
            context.assets
                .open("api_key.txt")
                .bufferedReader()
                .use(BufferedReader::readText)
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
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