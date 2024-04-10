package com.andreich.moviesearcher.di

import com.andreich.moviesearcher.data.network.ApiFactory
import com.andreich.moviesearcher.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @AppScope
    @Provides
    fun provideApiService(): ApiService {
        return ApiFactory.apiService
    }
}