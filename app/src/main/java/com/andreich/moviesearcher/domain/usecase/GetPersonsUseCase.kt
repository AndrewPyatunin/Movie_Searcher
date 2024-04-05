package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class GetPersonsUseCase(
    private val repository: MovieRepository
) {

    fun execute(movieId: Int) = repository.getPersons(movieId)
}