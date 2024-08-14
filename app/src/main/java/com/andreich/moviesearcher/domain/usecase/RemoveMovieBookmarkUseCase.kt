package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.repo.MovieRepository
import javax.inject.Inject

class RemoveMovieBookmarkUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend fun execute(movieId: Int) {
        repository.removeMovieBookmark(movieId)
    }
}