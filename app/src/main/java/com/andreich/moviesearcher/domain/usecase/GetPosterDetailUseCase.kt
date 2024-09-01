package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.repo.PosterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPosterDetailUseCase @Inject constructor(
    private val repository: PosterRepository
) {

    suspend fun execute(movieId: Int): Flow<List<Poster>> {
        return repository.getPostersDetail(movieId)
    }
}