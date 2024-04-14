package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.repo.PersonRepository
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    fun execute(movieId: Int, pageSize: Int, requestId: String) = repository.getPersons(movieId, pageSize, requestId)
}