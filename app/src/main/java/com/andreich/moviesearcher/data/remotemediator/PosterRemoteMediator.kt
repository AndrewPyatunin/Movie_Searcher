package com.andreich.moviesearcher.data.remotemediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.PosterRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.local.PosterDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.PosterEntity
import com.andreich.moviesearcher.data.entity.PosterRemoteKeyEntity
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.domain.pojo.PosterDto
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PosterRemoteMediator(
    private val apiKey: String,
    private val movieId: Int,
    private val requestId: String,
    private val database: MovieDatabase,
    private val posterRemoteKeyDao: PosterRemoteKeyDao,
    private val posterDataSource: PosterDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val posterMapper: MovieMapper<PosterDto, PosterEntity>
) : BaseRemoteMediator<PosterEntity, PosterRemoteKeyDao>(
    posterRemoteKeyDao,
    PosterEntity::class
) {

    override suspend fun workWithNetworkAndDatabase(page: Int, loadType: LoadType): MediatorResult {
        try {
            val apiResponse = remoteDataSource.getPosters(apiKey, page = page, movieId = movieId)
            val posters = apiResponse.docs
            val endOfPaginationReached = posters.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = posters.map {
                    PosterRemoteKeyEntity(
                        id = it.id ?: "",
                        valueId = it.id ?: "",
                        prevKey = prevKey ?: 0,
                        currentPage = page,
                        nextKey = nextKey ?: 0
                    )
                }

                posterRemoteKeyDao.insertAll(remoteKeys)
                posterDataSource.insertPosters(posters.map {
                    posterMapper.map(it, page, requestId)
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
        state: PagingState<Int, PosterEntity>
    ): MediatorResult {
        Log.d("MEDIATOR_POSTER", "load")
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

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, PosterEntity>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d("REMOTE_MEDIATOR_POSTER", "refresh")
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                Log.d("REMOTE_MEDIATOR_POSTER", "prepend")
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                Log.d("REMOTE_MEDIATOR_POSTER", "append, nextKey=$nextKey")
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = false)
            }
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PosterEntity>): PosterRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                posterRemoteKeyDao.getRemoteKeyByValueID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PosterEntity>): PosterRemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            Log.d("MEDIATOR_REMOTE", movie.id.toString())
            posterRemoteKeyDao.getRemoteKeyByValueID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PosterEntity>): PosterRemoteKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            posterRemoteKeyDao.getRemoteKeyByValueID(movie.id)
        }
    }
}