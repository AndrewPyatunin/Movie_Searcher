package com.andreich.moviesearcher.data.datasource.local

import com.andreich.moviesearcher.data.entity.MovieSearchHistoryEntity

interface HistoryDataSource {

    suspend fun insertRequest(request: MovieSearchHistoryEntity)

    suspend fun getHistory(): List<MovieSearchHistoryEntity>
}