package com.andreich.moviesearcher.data.datasource.local

import com.andreich.moviesearcher.data.database.SeasonDao
import com.andreich.moviesearcher.data.entity.SeasonEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeasonDataSourceImpl @Inject constructor(
    private val seasonDao: SeasonDao
) : SeasonDataSource {

    override fun getSeasons(movieId: Int): Flow<List<SeasonEntity>> {
        return seasonDao.getSeasons(movieId)
    }

    override suspend fun insertSeasons(list: List<SeasonEntity>) {
        seasonDao.insertSeasons(list)
    }
}