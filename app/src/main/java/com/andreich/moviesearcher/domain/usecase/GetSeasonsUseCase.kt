package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.model.Season
import com.andreich.moviesearcher.domain.repo.SeasonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSeasonsUseCase @Inject constructor(
    private val repository: SeasonRepository
) {

    suspend fun execute(movieId: Int): Flow<List<Season>> {
        return repository.getSeasons(movieId)
    }
}