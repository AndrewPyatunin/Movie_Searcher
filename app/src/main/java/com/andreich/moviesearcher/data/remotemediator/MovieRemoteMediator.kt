package com.andreich.moviesearcher.data.remotemediator

import androidx.paging.*
import androidx.room.withTransaction
import com.andreich.moviesearcher.data.database.*
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.*
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.domain.pojo.*
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val database: MovieDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String,
    private val remoteKeyDao: MovieRemoteKeyDao,
    private val movieMapper: MovieMapper<MovieDto, MovieEntity>,
    private val personMapper: MovieMapper<PersonDto, PersonEntity>,
    private val name: String? = null,
    private val requestId: Long,
) : BaseRemoteMediator<MovieEntity, MovieRemoteKeyDao>(remoteKeyDao, MovieEntity::class) {
    val movieDao = database.movieDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieEntity>): MediatorResult {
        return super.load(loadType, state)
    }

    override suspend fun workWithNetworkAndDatabase(page: Int, loadType: LoadType): MediatorResult {
        try {
            val apiResponse = name?.let {
                remoteDataSource.searchFilm(apiKey, page, movieName = name)
//                networkService.searchFilm(apiKey, page, movieName = name)
            } ?: remoteDataSource.searchWithFilters(page = page, apiKey = apiKey)
            val movies = apiResponse.docs
            movies.map {
                it.persons.map { person ->
                    personMapper.map(person, page, requestId)
                }
            }.forEach {
                database.personDao().insertActors(it)
            }
            if (name != null) {
                MovieSearchHistoryEntity(requestId, name, movies.map {
                    movieMapper.map(it, page, requestId)
                }).let {
                    database.historyDao().insertElement(it)
                }
            }
            val endOfPaginationReached = movies.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.APPEND) {

                }
                if (loadType == LoadType.REFRESH) {
//                    remoteKeyDao.clearRemoteKeys()
//                    movieDao.clearAllMovies()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map {
                    MovieRemoteKeyEntity(valueId = it.id ?: 0, prevKey = prevKey ?: 0, currentPage = page, nextKey = nextKey ?: 0)
                }

                remoteKeyDao.insertAll(remoteKeys)
                movieDao.insertMovies( movies.map {
                    movieMapper.map(it, page, requestId)
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

//    override suspend fun load(
//        val currentTime = System.currentTimeMillis()
//        val page: Int = when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1) ?: 1
//            }
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                val prevKey = remoteKeys?.prevKey
//                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//            }
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                val nextKey = remoteKeys?.nextKey
//                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//            }
//        }
//
//    }
//
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { id ->
//                remoteKeyDao.getRemoteKeyByValueID(id, Entities.Movies::class)
//            }
//        }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
//        return state.pages.firstOrNull {
//            it.data.isNotEmpty()
//        }?.data?.firstOrNull()?.let { movie ->
//            remoteKeyDao.getRemoteKeyByValueID(movie.id, Entities.Movies::class)
//        }
//    }
//
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
//        return state.pages.lastOrNull {
//            it.data.isNotEmpty()
//        }?.data?.lastOrNull()?.let { movie ->
//            remoteKeyDao.getRemoteKeyByValueID(movie.id, Entities.Movies::class)
//        }
//    }
}