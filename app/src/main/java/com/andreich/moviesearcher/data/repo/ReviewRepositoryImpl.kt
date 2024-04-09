package com.andreich.moviesearcher.data.repo

import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.MovieRemoteKeyDao
import com.andreich.moviesearcher.data.database.ReviewRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.home.MovieDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.ReviewRemoteMediator
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Review
import com.andreich.moviesearcher.domain.pojo.MovieDto
import com.andreich.moviesearcher.domain.pojo.PersonsDto
import com.andreich.moviesearcher.domain.pojo.ReviewDto
import com.andreich.moviesearcher.domain.repo.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReviewRepositoryImpl(
    private val database: MovieDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String,
    private val reviewMapper: MovieMapper<ReviewDto, ReviewEntity>,
    private val reviewEntityMapper: EntityToModelMapper<ReviewEntity, Review>,
    private val reviewRemoteKeyDao: ReviewRemoteKeyDao
) : ReviewRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getReviews(movieId: Int, pageSize: Int): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize
            ),
            pagingSourceFactory = { database.reviewDao().getReviews() },
            remoteMediator = ReviewRemoteMediator(
                database = database,
                remoteDataSource = remoteDataSource,
                apiKey = apiKey,
                reviewMapper = reviewMapper,
                remoteKeyDao = reviewRemoteKeyDao,
                movieId = movieId
            ),
        ).flow.map {
            it.map {
                reviewEntityMapper.map(it)
            }
        }
    }
}