package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class FetchFilmsUseCase(
    private val repository: MovieRepository
) {

    suspend fun execute() = repository.fetchFilms()
}