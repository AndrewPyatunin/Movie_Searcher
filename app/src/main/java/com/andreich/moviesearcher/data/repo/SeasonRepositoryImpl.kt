package com.andreich.moviesearcher.data.repo

import com.andreich.moviesearcher.data.database.SeasonDao
import com.andreich.moviesearcher.data.entity.SeasonEntity
import com.andreich.moviesearcher.domain.repo.SeasonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeasonRepositoryImpl @Inject constructor(
    private val seasonDao: SeasonDao
) : SeasonRepository {

    override fun getSeasons(): Flow<SeasonEntity> {
        return seasonDao.getSeasons()
    }
}