package com.andreich.moviesearcher.data.remotemediator

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
    private val requestId: Long,
    private val database: MovieDatabase,
    private val posterRemoteKeyDao: PosterRemoteKeyDao,
    private val posterDataSource: PosterDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val posterMapper: MovieMapper<PosterDto, PosterEntity>
) : BaseRemoteMediator<PosterEntity, PosterRemoteKeyDao>(
    posterRemoteKeyDao,
    PosterEntity::class
) {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PosterEntity>
    ): MediatorResult {
        return super.load(loadType, state)
    }

    override suspend fun workWithNetworkAndDatabase(page: Int, loadType: LoadType): MediatorResult {
        try {
            val apiResponse = remoteDataSource.getPosters(apiKey, page = page, movieId = movieId)
            val posters = apiResponse.docs
            val endOfPaginationReached = posters.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
//                    remoteKeyDao.clearRemoteKeys()
//                    movieDao.clearAllMovies()
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
}