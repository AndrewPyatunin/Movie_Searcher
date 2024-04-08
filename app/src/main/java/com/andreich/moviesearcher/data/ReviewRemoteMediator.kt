package com.andreich.moviesearcher.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.ReviewRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.Entities
import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.data.entity.ReviewRemoteKeyEntity
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.domain.pojo.ReviewDto
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ReviewRemoteMediator(
    private val apiKey: String,
    private val movieId: Int,
    private val remoteKeyDao: ReviewRemoteKeyDao,
    private val remoteDataSource: RemoteDataSource,
    private val database: MovieDatabase,
    private val reviewMapper: MovieMapper<ReviewDto, ReviewEntity>,
) :
    BaseRemoteMediator<ReviewEntity, ReviewRemoteKeyDao>(remoteKeyDao, ReviewEntity::class) {

    val reviewDao = database.reviewDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewEntity>
    ): MediatorResult {
        return super.load(loadType, state)
    }

    override suspend fun workWithNetworkAndDatabase(page: Int, loadType: LoadType): MediatorResult {
        try {
            val apiResponse = remoteDataSource.getReviews(apiKey, page, movieId = movieId)
            val reviews = apiResponse.docs
            val endOfPaginationReached = reviews.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
//                    remoteKeyDao.clearRemoteKeys()
//                    movieDao.clearAllMovies()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = reviews.map {
                    ReviewRemoteKeyEntity(
                        valueId = it.id ?: 0,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey,
                        valueType = Entities.Reviews::class
                    )
                }

                remoteKeyDao.insertAll(remoteKeys)
                reviewDao.insertReviews(reviews.map {
                    reviewMapper.map(it, page, System.currentTimeMillis())
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }
}