package com.andreich.moviesearcher.presentation.movie_list

import android.util.Log
import androidx.paging.PagingData
import com.andreich.moviesearcher.presentation.AnalyticsTracker
import ru.tinkoff.kotea.core.dsl.DslUpdate
import com.andreich.moviesearcher.presentation.movie_list.MovieListEvent.*
import com.andreich.moviesearcher.ui.screen.FilterFragment
import com.andreich.moviesearcher.ui.screen.MovieDetailFragment
import javax.inject.Inject

class MovieListUpdate @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) : DslUpdate<MovieListState, MovieListEvent, MovieListCommand, MovieListNews>() {

    override fun NextBuilder.update(event: MovieListEvent) {
        analyticsTracker.invoke(state, event)
        when (event) {
            is MovieListCommandsResultEvent.DataIsReady -> {
                handleResult(event)
            }
            is MovieListCommandsResultEvent.LoadError -> {
                handleResult(event)
            }
            is MovieListUiEvent.FilterSearchClicked -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.MovieItemClicked -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.SearchClicked -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.LoadData -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.FilterMoviesClicked -> {
                handleUiEvent(event)
            }
        }
    }

    private fun NextBuilder.handleResult(event: MovieListCommandsResultEvent) {
        when(event) {
            is MovieListCommandsResultEvent.DataIsReady -> {
                state { copy(isLoading = false, movies = event.movies) }
                Log.d("HANDLE_RESULT", "${state.isLoading}")
            }
            is MovieListCommandsResultEvent.LoadError -> {
                state { copy(isLoading = false) }
                news(MovieListNews.ShowErrorToast(event.message))
            }
        }
    }

    private fun NextBuilder.handleUiEvent(event: MovieListUiEvent) {
        when(event) {
            is MovieListUiEvent.FilterSearchClicked -> {
                commands(MovieListCommand.SearchFiltered(event.query, event.scope))
                state { copy(isLoading = true) }
            }
            is MovieListUiEvent.MovieItemClicked -> {
                news(MovieListNews.NavigateTo(MovieDetailFragment.getInstance(event.movie.id)))
            }
            is MovieListUiEvent.SearchClicked -> {
                Log.d("COMMAND_UI", "search_clicked_update ${event.name} ${event.requestId}")
                commands(MovieListCommand.SearchFilm(event.name, event.scope, event.requestId))
                state { copy(isLoading = true) }
            }
            is MovieListUiEvent.LoadData -> {
                Log.d("COMMAND_UI", "loadDataUpdate")
                commands(MovieListCommand.LoadData(event.scope))
                state { copy(isLoading = true) }
            }
            MovieListUiEvent.FilterMoviesClicked -> {
                news(MovieListNews.NavigateTo(FilterFragment.newInstance()))
            }
        }
    }
}