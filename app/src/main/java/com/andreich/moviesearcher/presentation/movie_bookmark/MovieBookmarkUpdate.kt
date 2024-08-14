package com.andreich.moviesearcher.presentation.movie_bookmark

import ru.tinkoff.kotea.core.dsl.DslUpdate
import javax.inject.Inject

class MovieBookmarkUpdate @Inject constructor() :
    DslUpdate<MovieBookmarkState, MovieBookmarkEvent, MovieBookmarkCommand, MovieBookmarkNews>() {

    override fun NextBuilder.update(event: MovieBookmarkEvent) {
        when (event) {
            is MovieBookmarkEvent.MovieBookmarkCommandResultEvent.RemoveSuccessful -> handleResult(
                event
            )
            is MovieBookmarkEvent.MovieBookmarkCommandResultEvent.DataIsReady -> handleResult(event)
            is MovieBookmarkEvent.MovieBookmarkCommandResultEvent.LoadError -> handleResult(event)
            is MovieBookmarkEvent.MovieBookmarkUiEvent.LoadMovies -> handleUiEvent(event)
            is MovieBookmarkEvent.MovieBookmarkUiEvent.NavigateTo -> handleUiEvent(event)
            is MovieBookmarkEvent.MovieBookmarkUiEvent.RemoveBookmark -> handleUiEvent(event)
        }
    }

    private fun NextBuilder.handleUiEvent(event: MovieBookmarkEvent.MovieBookmarkUiEvent) {
        when (event) {
            is MovieBookmarkEvent.MovieBookmarkUiEvent.LoadMovies -> {
                commands(MovieBookmarkCommand.LoadMovies)
                state { state.copy(isLoading = true) }
            }
            is MovieBookmarkEvent.MovieBookmarkUiEvent.NavigateTo -> {
                news(MovieBookmarkNews.NavigateTo(event.fragment))
            }
            is MovieBookmarkEvent.MovieBookmarkUiEvent.RemoveBookmark -> {
                commands(MovieBookmarkCommand.RemoveFromBookmark(event.movieId, event.movieTitle))
            }
        }
    }

    private fun NextBuilder.handleResult(event: MovieBookmarkEvent.MovieBookmarkCommandResultEvent) {
        when (event) {
            is MovieBookmarkEvent.MovieBookmarkCommandResultEvent.DataIsReady -> {
                state { state.copy(isLoading = false, movies = event.movies) }
            }
            is MovieBookmarkEvent.MovieBookmarkCommandResultEvent.LoadError -> {
                state { state.copy(isLoading = false) }
                news(MovieBookmarkNews.ShowError(event.message))
            }
            is MovieBookmarkEvent.MovieBookmarkCommandResultEvent.RemoveSuccessful -> {
                news(MovieBookmarkNews.ShowResult("Фильм ${event.movieTitle} успешно удален из избранного"))
            }
        }
    }
}