package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class GetMovieUseCase(
    private val repository: MovieRepository
) {

    fun execute(movieId: Int) = repository.getMovie(movieId)
}