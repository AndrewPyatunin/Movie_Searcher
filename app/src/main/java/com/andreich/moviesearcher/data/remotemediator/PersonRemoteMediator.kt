package com.andreich.moviesearcher.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.PersonRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.data.entity.PersonRemoteKeyEntity
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.domain.pojo.PersonsDto
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PersonRemoteMediator(
    private val apiKey: String,
    private val movieId: Int,
    private val requestId: Long,
    private val remoteKeyDao: PersonRemoteKeyDao,
    private val remoteDataSource: RemoteDataSource,
    private val database: MovieDatabase,
    private val personMapper: MovieMapper<PersonsDto, PersonEntity>
) :
    BaseRemoteMediator<PersonEntity, PersonRemoteKeyDao>(remoteKeyDao, PersonEntity::class) {

    val personDao = database.personDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PersonEntity>
    ): MediatorResult {
        return super.load(loadType, state)
    }

    override suspend fun workWithNetworkAndDatabase(page: Int, loadType: LoadType): MediatorResult {
        try {
            val apiResponse = remoteDataSource.getActors(apiKey, page, movieId = movieId)
            val persons = apiResponse.docs
            val endOfPaginationReached = persons.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
//                    remoteKeyDao.clearRemoteKeys()
//                    movieDao.clearAllMovies()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = persons.map {
                    PersonRemoteKeyEntity(
                        valueId = it.id ?: 0,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                remoteKeyDao.insertAll(remoteKeys)
                personDao.insertActors(persons.map {
                    personMapper.map(it, page, requestId)
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