package com.andreich.moviesearcher.presentation.actor_detail

import com.andreich.moviesearcher.domain.model.Actor
import kotlinx.coroutines.CoroutineScope

sealed interface ActorDetailEvent {

    sealed interface ActorDetailUiEvent : ActorDetailEvent {

        class LoadActor(val scope: CoroutineScope, val actorId: Int) : ActorDetailUiEvent

        class MovieItemClicked(val movieId: Int) : ActorDetailUiEvent

        object BackPress : ActorDetailUiEvent
    }

    sealed interface ActorDetailCommandsResultEvent : ActorDetailEvent {

        class DataIsReady(val actor: Actor) : ActorDetailCommandsResultEvent

        class LoadError(val message: String) : ActorDetailCommandsResultEvent
    }
}