package com.andreich.moviesearcher.data.remotemediator

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.andreich.moviesearcher.data.database.*
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.*
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.network.QUERY_PARAM_SORT_FIELD
import com.andreich.moviesearcher.data.network.QUERY_PARAM_SORT_TYPE
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
    private val requestId: String,
    private val filters: Map<String, List<String>> = emptyMap(),
    private val sortFilter: Map<String, String> = emptyMap(),
    private val completeRequest: Boolean
) : BaseRemoteMediator<MovieEntity, MovieRemoteKeyDao>(remoteKeyDao, MovieEntity::class, ) {
    val movieDao = database.movieDao()
    val historyDao = database.historyDao()

    override suspend fun workWithNetworkAndDatabase(page: Int, loadType: LoadType): MediatorResult {
        try {
            val apiResponse = name?.let {
                remoteDataSource.searchFilm(apiKey, page, 10, name)
            } ?: remoteDataSource.searchWithFilters(
                apiKey = apiKey,
                page = page,
                filters = filters,
                sortType = sortFilter.filterKeys {
                    it == QUERY_PARAM_SORT_TYPE
                },
                sortField = sortFilter
            )
            val movies = apiResponse.docs
            val endOfPaginationReached = apiResponse.page == apiResponse.total || apiResponse.docs.isEmpty()
            Log.d("MEDIATOR_PAGING", endOfPaginationReached.toString())
            database.withTransaction {
                name?.let {
                    if (it.trim().isNotEmpty()) {
                        MovieSearchHistoryEntity(movieTitle = name, id = name.lowercase().trim(), time = System.currentTimeMillis()).let {
                            if (historyDao.getTableSize() >= 20) {
                                historyDao.deleteOldest()
                            }
                            historyDao.insertElement(it)
                        }
                    }
                }

                movies.map {
                    it.persons.map { person ->
                        personMapper.map(person, page, requestId)
                    }
                }.forEach {
                    database.personDao().insertActors(it)
                }
                if (loadType == LoadType.REFRESH) {
                    Log.d("REMOTE_MEDIATOR", "refresh$page")
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                Log.d("REMOTE_MEDIATOR_PAGE", "next_key_first=$page")
                val remoteKeys = movies.map {
                    MovieRemoteKeyEntity(id = it.id ?: 0, valueId = it.id ?: 0, prevKey = prevKey, currentPage = page, nextKey = nextKey)
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

    override suspend fun initialize(): InitializeAction {
        Log.d("Mediator", "init")
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        Log.d("MEDIATOR", "load")
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

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieEntity>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                Log.d("REMOTE_MEDIATOR", "refresh")
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                Log.d("REMOTE_MEDIATOR", "prepend")
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                Log.d("REMOTE_MEDIATOR", "append, nextKey=$nextKey")
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = false)
            }
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): MovieRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.getRemoteKeyByValueID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): MovieRemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            remoteKeyDao.getRemoteKeyByValueID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): MovieRemoteKeyEntity? {
        Log.d("MEDIATOR_REMOTE_KEY_START", "movie.id.toString()")
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            Log.d("MEDIATOR_REMOTE_KEY", movie.id.toString())
            remoteKeyDao.getRemoteKeyByValueID(movie.id)
        }
    }
}