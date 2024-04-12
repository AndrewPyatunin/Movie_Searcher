package com.andreich.moviesearcher.di

import android.app.Application
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.data.network.ApiFactory
import com.andreich.moviesearcher.data.network.ApiService
import com.andreich.moviesearcher.data.network.CacheInterceptor
import com.andreich.moviesearcher.data.network.ForceCacheInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File
import javax.inject.Singleton

@Module
class ApiModule {

    private val BASE_URL = "https://api.kinopoisk.dev/v1.4/"
    private val application = MovieApp.getApplication()

    private val okHttpClient = OkHttpClient().newBuilder()
        .cache(Cache(File(application.cacheDir, "http-cache"), 10L * 1024L * 1024L)) // 10 MiB
        .addNetworkInterceptor(CacheInterceptor()) // only if Cache-Control header is not enabled from the server
        .addInterceptor(ForceCacheInterceptor(application))
        .build();

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build().create(ApiService::class.java)
    }
}