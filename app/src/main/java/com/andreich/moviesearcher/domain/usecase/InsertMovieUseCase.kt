package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.repo.MovieRepository
import javax.inject.Inject

class InsertMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend fun execute(movie: Movie) {
        repository.insertMovies(listOf(movie))
    }
}
