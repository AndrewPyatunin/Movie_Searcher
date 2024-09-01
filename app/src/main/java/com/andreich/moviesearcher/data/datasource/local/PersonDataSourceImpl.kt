package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.PersonDao
import com.andreich.moviesearcher.data.entity.ActorEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import javax.inject.Inject

class PersonDataSourceImpl @Inject constructor(
    private val personDao: PersonDao
) : PersonDataSource {

    override fun getPersons(movieId: Int): PagingSource<Int, PersonEntity> {
        return personDao.getActors(movieId)
    }

    override suspend fun insertActors(list: List<PersonEntity>) {
        personDao.insertActors(list)
    }

    override suspend fun insertActor(actor: ActorEntity) {
        personDao.insertActor(actor)
    }

    override suspend fun getActorDetail(actorId: Int): ActorEntity {
        return personDao.getActor(actorId)
    }
}