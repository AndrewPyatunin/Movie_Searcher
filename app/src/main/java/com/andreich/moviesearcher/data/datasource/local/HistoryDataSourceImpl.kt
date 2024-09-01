package com.andreich.moviesearcher.data.datasource.local

import com.andreich.moviesearcher.data.database.MovieSearchHistoryDao
import com.andreich.moviesearcher.data.entity.MovieSearchHistoryEntity
import javax.inject.Inject

class HistoryDataSourceImpl @Inject constructor(
    private val historyDao: MovieSearchHistoryDao
) : HistoryDataSource {

    override suspend fun insertRequest(request: MovieSearchHistoryEntity) {
        historyDao.insertElement(request)
    }

    override suspend fun getHistory(): List<MovieSearchHistoryEntity> {
        return historyDao.getValues()
    }
}