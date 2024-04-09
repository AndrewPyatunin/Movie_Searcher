package com.andreich.moviesearcher.data.datasource.home

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.PosterDetailEntity
import com.andreich.moviesearcher.data.entity.ReviewEntity

interface PosterDataSource {

    fun getPosters(): PagingSource<Int, PosterDetailEntity>

    suspend fun insertPosters(list: List<PosterDetailEntity>)
}