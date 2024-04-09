package com.andreich.moviesearcher.data.datasource.home

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.PosterEntity

interface PosterDataSource {

    fun getPosters(): PagingSource<Int, PosterEntity>

    suspend fun insertPosters(list: List<PosterEntity>)
}