package com.andreich.moviesearcher.presentation.actor_detail

import com.andreich.moviesearcher.ui.screen.MovieDetailFragment
import ru.tinkoff.kotea.core.dsl.DslUpdate
import javax.inject.Inject

class ActorDetailUpdate @Inject constructor() :
    DslUpdate<ActorDetailState, ActorDetailEvent, ActorDetailCommand, ActorDetailNews>() {

    override fun NextBuilder.update(event: ActorDetailEvent) {
        when(event) {
            is ActorDetailEvent.ActorDetailCommandsResultEvent.DataIsReady -> {
                handleResult(event)
            }
            is ActorDetailEvent.ActorDetailCommandsResultEvent.LoadError -> {
                handleResult(event)
            }
            is ActorDetailEvent.ActorDetailUiEvent.BackPress -> {
                handleUiEvent(event)
            }
            is ActorDetailEvent.ActorDetailUiEvent.LoadActor -> {
                handleUiEvent(event)
            }
            is ActorDetailEvent.ActorDetailUiEvent.MovieItemClicked -> {
                handleUiEvent(event)
            }
        }
    }

    private fun NextBuilder.handleResult(event: ActorDetailEvent.ActorDetailCommandsResultEvent) {
        when(event) {
            is ActorDetailEvent.ActorDetailCommandsResultEvent.DataIsReady -> {
                state { state.copy(actor = event.actor, isLoading = false) }
            }
            is ActorDetailEvent.ActorDetailCommandsResultEvent.LoadError -> {
                state { state.copy(isLoading = false) }
                news(ActorDetailNews.ShowError(event.message))
            }
        }
    }

    private fun NextBuilder.handleUiEvent(event: ActorDetailEvent.ActorDetailUiEvent) {
        when(event) {
            ActorDetailEvent.ActorDetailUiEvent.BackPress -> {
                state { state.copy(isLoading = false) }
                news(ActorDetailNews.BackPressed)
            }
            is ActorDetailEvent.ActorDetailUiEvent.LoadActor -> {
                state { state.copy(isLoading = true) }
                commands(ActorDetailCommand.GetActor(event.scope, event.actorId))
            }
            is ActorDetailEvent.ActorDetailUiEvent.MovieItemClicked -> {
                news(ActorDetailNews.NavigateTo(MovieDetailFragment.getInstance(event.movieId)))
            }
        }
    }
}