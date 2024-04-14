package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.ReviewEntity

interface ReviewDataSource {

    fun getReviews(movieId: Int): PagingSource<Int, ReviewEntity>

    suspend fun insertReviews(list: List<ReviewEntity>)
}