package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class SearchFilteredFilmsUseCase(
    private val repository: MovieRepository
) {

    suspend fun execute(searchParams: String) = repository.searchFilteredFilms(searchParams)
}