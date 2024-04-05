package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class SearchFilmUseCase(
    private val repository: MovieRepository
) {

    suspend fun execute(movieName: String) = repository.searchFilm(movieName)
}