package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.PosterEntity
import kotlinx.coroutines.flow.Flow

interface PosterDataSource {

    fun getPosters(movieId: Int): PagingSource<Int, PosterEntity>

    fun getPostersDetail(movieId: Int): Flow<List<PosterEntity>>

    suspend fun insertPosters(list: List<PosterEntity>)
}