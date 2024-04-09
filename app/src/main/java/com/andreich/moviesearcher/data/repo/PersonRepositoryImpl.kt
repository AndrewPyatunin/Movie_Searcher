package com.andreich.moviesearcher.data.repo

import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.MovieRemoteKeyDao
import com.andreich.moviesearcher.data.database.PersonRemoteKeyDao
import com.andreich.moviesearcher.data.database.ReviewRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.home.MovieDataSource
import com.andreich.moviesearcher.data.datasource.home.PersonDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.PersonRemoteMediator
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.model.Review
import com.andreich.moviesearcher.domain.pojo.MovieDto
import com.andreich.moviesearcher.domain.pojo.PersonsDto
import com.andreich.moviesearcher.domain.pojo.ReviewDto
import com.andreich.moviesearcher.domain.repo.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PersonRepositoryImpl(
    private val database: MovieDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val personDataSource: PersonDataSource,
    private val apiKey: String,
    private val requestId: Long,
    private val personMapper: MovieMapper<PersonsDto, PersonEntity>,
    private val personEntityMapper: EntityToModelMapper<PersonEntity, Person>,
    private val remoteKeyDao: PersonRemoteKeyDao,
) : PersonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPersons(movieId: Int, pageSize: Int): Flow<PagingData<Person>> {
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