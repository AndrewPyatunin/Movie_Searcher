package com.andreich.moviesearcher.data.network

import android.content.Context
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

class CacheInterceptor(val context: Context) : Interceptor {

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
//        Log.d("INTERCEPTOR", apiKey)
        Log.d("INTERCEPTOR", "intercept")
        val response: Response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(7, TimeUnit.DAYS)
            .build()
        return response.newBuilder()
            .header("X-API-KEY", apiKey)
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}