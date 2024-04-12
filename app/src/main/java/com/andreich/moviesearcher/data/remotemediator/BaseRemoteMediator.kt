package com.andreich.moviesearcher.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.andreich.moviesearcher.data.database.MovieRemoteKeyDao
import com.andreich.moviesearcher.data.database.PersonRemoteKeyDao
import com.andreich.moviesearcher.data.database.PosterRemoteKeyDao
import com.andreich.moviesearcher.data.database.ReviewRemoteKeyDao
import com.andreich.moviesearcher.data.entity.*
import kotlin.reflect.KClass

@OptIn(ExperimentalPagingApi::class)
open class BaseRemoteMediator<Entity : Any, RemoteKey : Any>(
    private val remoteKeyDao: RemoteKey,
    private val entityClass: KClass<out Any>,
) : RemoteMediator<Int, Entity>() {

    open suspend fun workWithNetworkAndDatabase(page: Int, loadType: LoadType): MediatorResult {
        return MediatorResult.Success(endOfPaginationReached = false)
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Entity>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state, entityClass)
                when (remoteKeys) {
                    is MovieRemoteKeyEntity? -> remoteKeys?.nextKey?.minus(1) ?: 1
                    is PersonRemoteKeyEntity? -> remoteKeys?.nextKey?.minus(1) ?: 1
                    is PosterRemoteKeyEntity? -> remoteKeys?.nextKey?.minus(1) ?: 1
                    is ReviewRemoteKeyEntity? -> remoteKeys?.nextKey?.minus(1) ?: 1
                    else -> throw RuntimeException("Unexpected type of remoteKeys")
                }

            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state, entityClass)
                when (remoteKeys) {
                    is PosterRemoteKeyEntity? -> {
                        val prevKey = remoteKeys?.prevKey
                        prevKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    is MovieRemoteKeyEntity? -> {
                        val prevKey = remoteKeys?.prevKey
                        prevKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    is PersonRemoteKeyEntity? -> {
                        val prevKey = remoteKeys?.prevKey
                        prevKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    is ReviewRemoteKeyEntity? -> {
                        val prevKey = remoteKeys?.prevKey
                        prevKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    else -> throw RuntimeException("Unexpected type of remoteKeys")
                }

            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state, entityClass)
                when (remoteKeys) {
                    is MovieRemoteKeyEntity? -> {
                        val nextKey = remoteKeys?.nextKey
                        nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    is PersonRemoteKeyEntity? -> {
                        val nextKey = remoteKeys?.nextKey
                        nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    is ReviewRemoteKeyEntity? -> {
                        val nextKey = remoteKeys?.nextKey
                        nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    is PosterRemoteKeyEntity? -> {
                        val nextKey = remoteKeys?.nextKey
                        nextKey
                            ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                    else -> throw RuntimeException("Unexpected type of remoteKeys")
                }
            }
        }
        return workWithNetworkAndDatabase(page, loadType)

    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Entity>,
        entityClass: KClass<out Any>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            when (entityClass) {
                MovieEntity::class -> {
                    (state.closestItemToPosition(position) as MovieEntity).id.let { id ->
                        (remoteKeyDao as MovieRemoteKeyDao).getRemoteKeyByValueID(id)
                    } as RemoteKey?
                }
                PersonEntity::class -> {
                    (state.closestItemToPosition(position) as PersonEntity).id.let { id ->
                        (remoteKeyDao as PersonRemoteKeyDao).getRemoteKeyByValueID(id)
                    } as RemoteKey?
                }
                ReviewEntity::class -> {
                    (state.closestItemToPosition(position) as PersonEntity).id.let { id ->
                        (remoteKeyDao as ReviewRemoteKeyDao).getRemoteKeyByValueID(id)
                    } as RemoteKey?
                }
                PosterEntity::class -> {
                    (state.closestItemToPosition(position) as PosterEntity).id.let { id ->
                        (remoteKeyDao as PosterRemoteKeyDao).getRemoteKeyByValueID(id)
                    } as RemoteKey?
                }
                else -> {
                    throw RuntimeException("Unexpected type of entityClass")
                }
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Entity>,
        entityClass: KClass<out Any>
    ): RemoteKey? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            when (entityClass) {
                MovieEntity::class -> {
                    (remoteKeyDao as MovieRemoteKeyDao).getRemoteKeyByValueID(
                        (movie as MovieEntity).id
                    ) as RemoteKey?
                }
                PersonEntity::class -> {
                    (remoteKeyDao as PersonRemoteKeyDao).getRemoteKeyByValueID(
                        (movie as PersonEntity).id
                    ) as RemoteKey?
                }
                ReviewEntity::class -> {
                    (remoteKeyDao as ReviewRemoteKeyDao).getRemoteKeyByValueID(
                        (movie as ReviewEntity).id
                    ) as RemoteKey?
                }
                PosterEntity::class -> {
                    (remoteKeyDao as PosterRemoteKeyDao).getRemoteKeyByValueID(
                        (movie as PosterEntity).id
                    ) as RemoteKey?
                }
                else -> throw RuntimeException("Unexpected type of entityClass")
            }

        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Entity>,
        entityClass: KClass<out Any>
    ): RemoteKey? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            when (entityClass) {
                MovieEntity::class -> {
                    (remoteKeyDao as MovieRemoteKeyDao).getRemoteKeyByValueID(
                        (movie as MovieEntity).id
                    ) as RemoteKey?
                }
                PersonEntity::class -> {
                    (remoteKeyDao as PersonRemoteKeyDao).getRemoteKeyByValueID(
                        (movie as PersonEntity).id
                    ) as RemoteKey?
                }
                ReviewEntity::class -> {
                    (remoteKeyDao as ReviewRemoteKeyDao).getRemoteKeyByValueID(
                        (movie as ReviewEntity).id
                    ) as RemoteKey?
                }
                PosterEntity::class -> {
                    (remoteKeyDao as PosterRemoteKeyDao).getRemoteKeyByValueID(
                        (movie as PosterEntity).id
                    ) as RemoteKey?
                }
                else -> throw RuntimeException("Unexpected type of entityClass")
            }

        }
    }
}