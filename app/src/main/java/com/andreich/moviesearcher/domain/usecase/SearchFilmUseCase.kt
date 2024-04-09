package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.repo.MovieRepository

class SearchFilmUseCase(
    private val repository: MovieRepository
) {

    suspend fun execute(movieName: String) = repository.searchFilm(movieName)
}