package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieBookmarkUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    fun execute(movieId: Int): Flow<Movie?> {
        return repository.getMovieBookmark(movieId)
    }
}