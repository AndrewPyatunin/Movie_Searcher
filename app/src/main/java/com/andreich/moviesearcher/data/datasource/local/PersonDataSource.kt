package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.PersonEntity

interface PersonDataSource {

    fun getPersons(): PagingSource<Int, PersonEntity>

    suspend fun insertActors(list: List<PersonEntity>)
}