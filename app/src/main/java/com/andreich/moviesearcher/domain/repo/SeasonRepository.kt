package com.andreich.moviesearcher.domain.repo

import com.andreich.moviesearcher.data.entity.SeasonEntity
import kotlinx.coroutines.flow.Flow

interface SeasonRepository {

    fun getSeasons(): Flow<SeasonEntity>
}