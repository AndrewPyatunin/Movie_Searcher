package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.model.Actor
import com.andreich.moviesearcher.domain.repo.PersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorUseCase @Inject constructor(
    private val repository: PersonRepository
) {

    suspend fun execute(actorId: Int): Actor {
        return repository.getActor(actorId)
    }
}