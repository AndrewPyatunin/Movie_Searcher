package com.andreich.moviesearcher.data.repo

import android.util.Log
import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.PosterRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.local.PosterDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.PosterEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.PosterRemoteMediator
import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.pojo.PosterDto
import com.andreich.moviesearcher.domain.repo.PosterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PosterRepositoryImpl @Inject constructor(
    private val apiKey: String,
    private val remoteDataSource: RemoteDataSource,
    private val database: MovieDatabase,
    private val posterDataSource: PosterDataSource,
    private val posterMapper: MovieMapper<PosterDto, PosterEntity>,
    private val posterRemoteKeyDao: PosterRemoteKeyDao,
    private val posterEntityMapper: EntityToModelMapper<PosterEntity, Poster>
) : PosterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPosters(movieId: Int, pageSize: Int, requestId: String): Flow<PagingData<Poster>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize
            ),
            remoteMediator = PosterRemoteMediator(
                apiKey,
                movieId,
                requestId,
                database,
                posterRemoteKeyDao,
                posterDataSource,
                remoteDataSource,
                posterMapper
            ),
            pagingSourceFactory = { posterDataSource.getPosters(movieId) }
        ).flow.map {
            it.map {
                posterEntityMapper.map(it)
            }
        }
    }

    override suspend fun getPostersDetail(movieId: Int): Flow<List<Poster>> {
               return posterDataSource.insertPosters(remoteDataSource.getPosters(apiKey, movieId).let {
                    it.docs.map { poster ->
                        posterMapper.map(poster, it.page ?: 1, "")
                    }
                }).let {
                    posterDataSource.getPostersDetail(movieId).map {
                        Log.d("POSTER_REPO", it.joinToString())
                        it.map { posterEntityMapper.map(it)}
                    }
                }
    }
}