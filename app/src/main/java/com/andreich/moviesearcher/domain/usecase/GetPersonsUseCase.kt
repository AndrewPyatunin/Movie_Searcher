package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.repo.PersonRepository

class GetPersonsUseCase(
    private val repository: PersonRepository
) {

    fun execute(movieId: Int, pageSize: Int) = repository.getPersons(movieId, pageSize)
}