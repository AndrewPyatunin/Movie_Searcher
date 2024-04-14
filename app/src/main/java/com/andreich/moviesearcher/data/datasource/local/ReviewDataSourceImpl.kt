package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.ReviewDao
import com.andreich.moviesearcher.data.entity.ReviewEntity
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(
    private val reviewDao: ReviewDao
) : ReviewDataSource {

    override fun getReviews(movieId: Int): PagingSource<Int, ReviewEntity> {
        return reviewDao.getReviews(movieId)
    }

    override suspend fun insertReviews(list: List<ReviewEntity>) {
        reviewDao.insertReviews(list)
    }
}