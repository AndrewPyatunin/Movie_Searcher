package com.andreich.moviesearcher.data.remotemediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.ReviewRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.local.ReviewDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
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
    private val reviewDataSource: ReviewDataSource,
    private val requestId: String,
    private val reviewMapper: MovieMapper<ReviewDto, ReviewEntity>,
) :
    BaseRemoteMediator<ReviewEntity, ReviewRemoteKeyDao>(remoteKeyDao, ReviewEntity::class) {

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
                        id = it.id ?: 0,
                        valueId = it.id ?: 0,
                        prevKey = prevKey ?: 0,
                        currentPage = page,
                        nextKey = nextKey ?: 0
                    )
                }

                remoteKeyDao.insertAll(remoteKeys)
                reviewDataSource.insertReviews(reviews.map {
                    reviewMapper.map(it, page, requestId)
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewEntity>
    ): MediatorResult {
        Log.d("MEDIATOR_REVIEW", "load")
        val pageKeyData = getKeyPageData(loadType, state)
        val page: Int = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        return workWithNetworkAndDatabase(page, loadType)
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, ReviewEntity>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d("REMOTE_MEDIATOR_REVIEW", "refresh")
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                Log.d("REMOTE_MEDIATOR_REVIEW", "prepend")
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                Log.d("REMOTE_MEDIATOR_REVIEW", "append, nextKey=$nextKey")
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = false)
            }
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ReviewEntity>): ReviewRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.getRemoteKeyByValueID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ReviewEntity>): ReviewRemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            Log.d("MEDIATOR_REMOTE", movie.id.toString())
            remoteKeyDao.getRemoteKeyByValueID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ReviewEntity>): ReviewRemoteKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            remoteKeyDao.getRemoteKeyByValueID(movie.id)
        }
    }
}