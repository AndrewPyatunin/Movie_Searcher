package com.andreich.moviesearcher.domain.repo

import com.andreich.moviesearcher.domain.model.Season
import kotlinx.coroutines.flow.Flow

interface SeasonRepository {

    suspend fun getSeasons(movieId: Int): Flow<List<Season>>
}