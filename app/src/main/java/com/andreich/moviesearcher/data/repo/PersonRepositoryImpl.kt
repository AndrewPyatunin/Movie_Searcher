package com.andreich.moviesearcher.data.repo

import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.PersonRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.local.PersonDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.PersonRemoteMediator
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.pojo.PersonDto
import com.andreich.moviesearcher.domain.repo.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val database: MovieDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val personDataSource: PersonDataSource,
    private val apiKey: String,
    private val personMapper: MovieMapper<PersonDto, PersonEntity>,
    private val personEntityMapper: EntityToModelMapper<PersonEntity, Person>,
    private val remoteKeyDao: PersonRemoteKeyDao,
) : PersonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPersons(movieId: Int, pageSize: Int, requestId: String): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize,
            ),
            remoteMediator = PersonRemoteMediator(
                apiKey, movieId, requestId, remoteKeyDao, remoteDataSource, database, personMapper
            ),
            pagingSourceFactory = { personDataSource.getPersons() }
        ).flow.map {
            it.map {
                personEntityMapper.map(it)
            }
        }
    }
}