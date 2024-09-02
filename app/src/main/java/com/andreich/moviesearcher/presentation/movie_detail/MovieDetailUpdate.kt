package com.andreich.moviesearcher.presentation.movie_detail

import android.util.Log
import androidx.paging.map
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
            is MovieDetailCommandsResultEvent.Success -> handleResult(event)
            is MovieDetailCommandsResultEvent.SeasonIsReady -> handleResult(event)
            is MovieDetailCommandsResultEvent.ActorsIsReady -> handleResult(event)
            is MovieDetailCommandsResultEvent.MovieIsReady -> handleResult(event)
            is MovieDetailCommandsResultEvent.PostersIsReady -> handleResult(event)
            is MovieDetailCommandsResultEvent.ReviewsIsReady -> handleResult(event)
            is MovieDetailUiEvent.BackPress -> handleUiEvent(event)
            is MovieDetailUiEvent.LoadMovie -> handleUiEvent(event)
            is MovieDetailUiEvent.NavigateTo -> handleUiEvent(event)
            is MovieDetailUiEvent.AddToBookmark -> handleUiEvent(event)
            is MovieDetailUiEvent.LoadSeasons -> handleUiEvent(event)
            is MovieDetailUiEvent.LoadActors -> handleUiEvent(event)
            is MovieDetailUiEvent.LoadPosters -> handleUiEvent(event)
            is MovieDetailUiEvent.LoadReviews -> handleUiEvent(event)
        }
    }

    private fun NextBuilder.handleResult(event: MovieDetailCommandsResultEvent) {
        when (event) {
            is MovieDetailCommandsResultEvent.DataIsReady -> {
                event.actors.map {
                    Log.d("ACTORS_MOVIE_DETAIL_UPDATE", it.name.toString())
                }
                state {
                    state.copy(
                        isBookmark = event.isBookmark,
                        isLoading = false,
                        movie = event.movie,
                        reviews = event.reviews,
                        posters = event.posters,
                        persons = event.actors
                    )
                }
            }
            is MovieDetailCommandsResultEvent.LoadError -> {
                state { state.copy(isLoading = false) }
                news(MovieDetailNews.ShowError(event.message))
            }
            is MovieDetailCommandsResultEvent.Success -> {
                state { state.copy(isBookmark = event.isBookmark) }
                news(MovieDetailNews.ShowToast(if (event.isBookmark) "Фильм добавлен в избранное" else "Фильм удален из избранного"))
            }
            is MovieDetailCommandsResultEvent.SeasonIsReady -> {
                state { state.copy(seasons = event.seasons) }
            }
            is MovieDetailCommandsResultEvent.ActorsIsReady -> {
                state { state.copy(persons = event.actors) }
            }
            is MovieDetailCommandsResultEvent.MovieIsReady -> {
                state { state.copy(movie = event.movie, isBookmark = event.isBookmark, isLoading = false) }
            }
            is MovieDetailCommandsResultEvent.PostersIsReady -> {
                state { state.copy(posters = event.posters) }
            }
            is MovieDetailCommandsResultEvent.ReviewsIsReady -> {
                state { state.copy(reviews = event.reviews) }
            }
        }
    }

    private fun NextBuilder.handleUiEvent(event: MovieDetailUiEvent) {
        when (event) {
            MovieDetailUiEvent.BackPress -> {

            }
            is MovieDetailUiEvent.LoadMovie -> {
                Log.d("ACTORS_MOVIE_COMMAND", event.movieId.toString())
                state { state.copy(isLoading = true) }
                commands(MovieDetailCommand.LoadMovie(event.movieId, event.scope))
            }
            is MovieDetailUiEvent.NavigateTo -> {
                news(MovieDetailNews.NavigateTo(event.fragment))
            }
            is MovieDetailUiEvent.AddToBookmark -> {
                commands(MovieDetailCommand.AddToBookmark(event.movieId, event.isBookmark))
            }
            is MovieDetailUiEvent.LoadSeasons -> {
                commands(MovieDetailCommand.LoadSeasons(event.movieId))
            }
            is MovieDetailUiEvent.LoadActors -> {
                commands(MovieDetailCommand.LoadPersons(event.movieId, event.scope))
            }
            is MovieDetailUiEvent.LoadPosters -> {
                commands(MovieDetailCommand.LoadPosters(event.movieId, event.scope))
            }
            is MovieDetailUiEvent.LoadReviews -> {
                commands(MovieDetailCommand.LoadReviews(event.movieId, event.scope))
            }
        }
    }
}