package com.andreich.moviesearcher.presentation.movie_detail

import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailEvent.MovieDetailCommandsResultEvent
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailEvent.MovieDetailUiEvent
import ru.tinkoff.kotea.core.dsl.DslUpdate
import javax.inject.Inject

class MovieDetailUpdate @Inject constructor() :
    DslUpdate<MovieDetailState, MovieDetailEvent, MovieDetailCommand, MovieDetailNews>() {

    override fun NextBuilder.update(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailCommandsResultEvent.DataIsReady -> handleResult(event)
            is MovieDetailCommandsResultEvent.LoadError -> handleResult(event)
            is MovieDetailUiEvent.BackPress -> handleUiEvent(event)
            is MovieDetailUiEvent.LoadMovie -> handleUiEvent(event)
        }
    }

    private fun NextBuilder.handleResult(event: MovieDetailCommandsResultEvent) {
        when (event) {
            is MovieDetailCommandsResultEvent.DataIsReady -> {
                state { state.copy(isLoading = false, movie = event.movie, reviews = event.reviews, posters = event.posters, persons = event.actors) }
            }
            is MovieDetailCommandsResultEvent.LoadError -> {
                state { state.copy(isLoading = false) }
                news(MovieDetailNews.ShowError(event.message))
            }
        }
    }

    private fun NextBuilder.handleUiEvent(event: MovieDetailUiEvent) {
        when (event) {
            MovieDetailUiEvent.BackPress -> {

            }
            is MovieDetailUiEvent.LoadMovie -> {
                state { state.copy(true) }
                commands(MovieDetailCommand.LoadMovie(event.movieId, event.scope))
            }
        }
    }
}