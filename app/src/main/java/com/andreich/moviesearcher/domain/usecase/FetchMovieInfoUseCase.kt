package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class FetchMovieInfoUseCase(
    private val repository: MovieRepository
) {

    suspend fun execute(movieId: Int) = repository.fetchFilmInfo(movieId)
}