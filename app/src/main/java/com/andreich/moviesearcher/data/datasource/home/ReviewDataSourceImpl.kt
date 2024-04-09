package com.andreich.moviesearcher.data.datasource.home

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.ReviewDao
import com.andreich.moviesearcher.data.entity.ReviewEntity
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(
    private val reviewDao: ReviewDao
) : ReviewDataSource {

    override fun getReviews(): PagingSource<Int, ReviewEntity> {
        return reviewDao.getReviews()
    }

    override suspend fun insertReviews(list: List<ReviewEntity>) {
        reviewDao.insertReviews(list)
    }
}