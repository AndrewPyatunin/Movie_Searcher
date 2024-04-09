package com.andreich.moviesearcher.data.repo

import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.PosterRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.home.PosterDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.PosterEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.PosterRemoteMediator
import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.pojo.PosterDto
import com.andreich.moviesearcher.domain.repo.PosterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PosterRepositoryImpl @Inject constructor(
    private val apiKey: String,
    private val remoteDataSource: RemoteDataSource,
    private val database: MovieDatabase,
    private val requestId: Long,
    private val posterDataSource: PosterDataSource,
    private val posterMapper: MovieMapper<PosterDto, PosterEntity>,
    private val posterRemoteKeyDao: PosterRemoteKeyDao,
    private val posterEntityMapper: EntityToModelMapper<PosterEntity, Poster>
) : PosterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPosters(movieId: Int, pageSize: Int): Flow<PagingData<Poster>> {
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
            pagingSourceFactory = { posterDataSource.getPosters() }
        ).flow.map {
            it.map {
                posterEntityMapper.map(it)
            }
        }
    }
}