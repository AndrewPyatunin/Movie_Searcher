package com.andreich.moviesearcher.domain.repo

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    fun getPersons(movieId: Int, pageSize: Int): Flow<PagingData<Person>>
}