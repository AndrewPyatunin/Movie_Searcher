package com.andreich.moviesearcher.data.repo

import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.ReviewRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.local.ReviewDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.ReviewRemoteMediator
import com.andreich.moviesearcher.domain.model.Review
import com.andreich.moviesearcher.domain.pojo.ReviewDto
import com.andreich.moviesearcher.domain.repo.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val database: MovieDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val reviewDataSource: ReviewDataSource,
    private val apiKey: String,
    private val reviewMapper: MovieMapper<ReviewDto, ReviewEntity>,
    private val reviewEntityMapper: EntityToModelMapper<ReviewEntity, Review>,
    private val reviewRemoteKeyDao: ReviewRemoteKeyDao
) : ReviewRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getReviews(movieId: Int, pageSize: Int, requestId: String): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize
            ),
            pagingSourceFactory = { database.reviewDao().getReviews(movieId) },
            remoteMediator = ReviewRemoteMediator(
                apiKey = apiKey,
                movieId = movieId,
                remoteKeyDao = reviewRemoteKeyDao,
                remoteDataSource = remoteDataSource,
                database = database,
                reviewDataSource = reviewDataSource,
                requestId = requestId,
                reviewMapper = reviewMapper
            ),
        ).flow.map {
            it.map {
                reviewEntityMapper.map(it)
            }
        }
    }
}