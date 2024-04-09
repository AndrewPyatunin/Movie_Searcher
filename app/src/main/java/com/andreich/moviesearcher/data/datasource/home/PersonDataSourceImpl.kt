package com.andreich.moviesearcher.data.datasource.home

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.PersonEntity

class PersonDataSourceImpl : PersonDataSource {

    override fun getPersons(): PagingSource<Int, PersonEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertActors(list: List<PersonEntity>) {
        TODO("Not yet implemented")
    }
}