package com.andreich.moviesearcher.domain.usecase

import androidx.paging.cachedIn
import com.andreich.moviesearcher.domain.repo.PersonRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    fun execute(movieId: Int, pageSize: Int = 10, requestId: String = "", scope: CoroutineScope) = repository.getPersons(movieId, pageSize, requestId).cachedIn(scope)
}