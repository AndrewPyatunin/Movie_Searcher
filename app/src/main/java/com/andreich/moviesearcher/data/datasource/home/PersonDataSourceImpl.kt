package com.andreich.moviesearcher.data.datasource.home

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.PersonDao
import com.andreich.moviesearcher.data.entity.PersonEntity
import javax.inject.Inject

class PersonDataSourceImpl @Inject constructor(
    private val personDao: PersonDao
) : PersonDataSource {

    override fun getPersons(): PagingSource<Int, PersonEntity> {
        return personDao.getActors()
    }

    override suspend fun insertActors(list: List<PersonEntity>) {
        personDao.insertActors(list)
    }
}