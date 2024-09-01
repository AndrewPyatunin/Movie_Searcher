package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.ActorEntity
import com.andreich.moviesearcher.data.entity.PersonEntity

interface PersonDataSource {

    fun getPersons(movieId: Int): PagingSource<Int, PersonEntity>

    suspend fun getActorDetail(actorId: Int): ActorEntity

    suspend fun insertActors(list: List<PersonEntity>)

    suspend fun insertActor(actor: ActorEntity)
}