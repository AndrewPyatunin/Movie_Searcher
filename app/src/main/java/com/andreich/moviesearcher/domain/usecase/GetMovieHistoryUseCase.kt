package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.model.MovieSearchHistory
import com.andreich.moviesearcher.domain.repo.MovieRepository
import javax.inject.Inject

class GetMovieHistoryUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend fun execute(): List<MovieSearchHistory> {
        return repository.getMovieHistory()
    }
}