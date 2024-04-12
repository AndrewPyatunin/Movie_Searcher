package com.andreich.moviesearcher.data.network

import android.app.Application
import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object ApiFactory {

    private val BASE_URL = "https://api.kinopoisk.dev/v1.4/"
    private val application: Application = Application()

     private val okHttpClient = OkHttpClient().newBuilder()
        .cache(Cache(File(application.cacheDir, "http-cache"), 10L * 1024L * 1024L)) // 10 MiB
        .addNetworkInterceptor(CacheInterceptor()) // only if Cache-Control header is not enabled from the server
        .addInterceptor(ForceCacheInterceptor(application))
        .build();

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()
}