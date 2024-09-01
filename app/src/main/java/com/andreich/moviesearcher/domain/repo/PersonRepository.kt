package com.andreich.moviesearcher.domain.repo

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Actor
import com.andreich.moviesearcher.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun getPersons(movieId: Int, pageSize: Int, requestId: String): Flow<PagingData<Person>>

    suspend fun getActor(actorId: Int) : Actor
}