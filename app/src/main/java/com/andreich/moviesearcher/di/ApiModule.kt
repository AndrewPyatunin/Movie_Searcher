package com.andreich.moviesearcher.di

import android.content.Context
import com.andreich.moviesearcher.data.network.ApiKeyInterceptor
import com.andreich.moviesearcher.data.network.ApiService
import com.andreich.moviesearcher.data.network.CacheInterceptor
import com.andreich.moviesearcher.data.network.ForceCacheInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

@Module
class ApiModule {

    private val BASE_URL = "https://api.kinopoisk.dev/v1.4/"

    @Provides
    fun provideApiService(context: Context): ApiService {
        val okHttpClient = OkHttpClient().newBuilder()
            .cache(Cache(File(context.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .addInterceptor(ApiKeyInterceptor(context))
            .addNetworkInterceptor(CacheInterceptor(context))
            .addInterceptor(ForceCacheInterceptor(context))
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build().create(ApiService::class.java)
    }
}