package com.andreich.moviesearcher.data.repo

import android.util.Log
import androidx.paging.*
import androidx.room.withTransaction
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.PersonRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.local.PersonDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.ActorEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.data.mapper.DtoMapper
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.PersonRemoteMediator
import com.andreich.moviesearcher.domain.model.Actor
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.pojo.ActorDto
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
    private val actorMapper: MovieMapper<ActorDto, ActorEntity>,
    private val personMapper: MovieMapper<PersonDto, PersonEntity>,
    private val personEntityMapper: EntityToModelMapper<PersonEntity, Person>,
    private val actorEntityMapper: EntityToModelMapper<ActorEntity, Actor>,
    private val remoteKeyDao: PersonRemoteKeyDao,
) : PersonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPersons(
        movieId: Int,
        pageSize: Int,
        requestId: String
    ): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize,
            ),
            remoteMediator = PersonRemoteMediator(
                apiKey, movieId, requestId, remoteKeyDao, remoteDataSource, database, personMapper
            ),
            pagingSourceFactory = { personDataSource.getPersons(movieId) }
        ).flow.map {
            it.map {
                personEntityMapper.map(it)
            }
        }
    }

    override suspend fun getActor(actorId: Int): Actor {
        return remoteDataSource.getActor(actorId).let {
            actorMapper.map(it, 0, "").let {
                Log.d("PERSON_REPO", it.name)
                database.withTransaction {
                    personDataSource.insertActor(it)
                    actorEntityMapper.map(personDataSource.getActorDetail(actorId))
                }
            }
        }
    }
}