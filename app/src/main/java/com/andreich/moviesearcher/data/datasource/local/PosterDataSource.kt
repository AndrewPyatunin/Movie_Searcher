package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.PosterEntity

interface PosterDataSource {

    fun getPosters(movieId: Int): PagingSource<Int, PosterEntity>

    suspend fun insertPosters(list: List<PosterEntity>)
}