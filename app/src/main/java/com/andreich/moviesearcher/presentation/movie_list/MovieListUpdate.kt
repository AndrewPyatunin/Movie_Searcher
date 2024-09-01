package com.andreich.moviesearcher.presentation.movie_list

import android.util.Log
import ru.tinkoff.kotea.core.dsl.DslUpdate
import com.andreich.moviesearcher.presentation.movie_list.MovieListEvent.*
import com.andreich.moviesearcher.ui.screen.MovieBookmarkFragment
import com.andreich.moviesearcher.ui.screen.MovieFilterFragment
import com.andreich.moviesearcher.ui.screen.MovieDetailFragment
import javax.inject.Inject

class MovieListUpdate @Inject constructor(
) : DslUpdate<MovieListState, MovieListEvent, MovieListCommand, MovieListNews>() {

    override fun NextBuilder.update(event: MovieListEvent) {
        when (event) {
            is MovieListCommandsResultEvent.DataIsReady -> {
                handleResult(event)
            }
            is MovieListCommandsResultEvent.LoadError -> {
                handleResult(event)
            }
            is MovieListCommandsResultEvent.SearchHistoryIsReady -> {
                handleResult(event)
            }
            is MovieListUiEvent.FilterSearchClicked -> {
                handleUiEvent(event)
            }
            is MovieListCommandsResultEvent.AdditionSuccess -> {
                handleResult(event)
            }
            is MovieListCommandsResultEvent.RemovalSuccess -> {
                handleResult(event)
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
            is MovieListUiEvent.PaginationLoad -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.PaginationStopLoad -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.GetHistory -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.SortedSearchCLicked -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.FavouritesClicked -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.AddToBookmarkClicked -> {
                handleUiEvent(event)
            }
            is MovieListUiEvent.RemoveFromBookmarkClicked -> {
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
            is MovieListCommandsResultEvent.SearchHistoryIsReady -> {
                Log.d("HISTORY_HANDLE", event.history.size.toString())
                news(MovieListNews.ShowHistory(event.history))
            }
            is MovieListCommandsResultEvent.AdditionSuccess -> {
                news(MovieListNews.ShowBookmarkToast(event.movieTitle, true))
            }
            is MovieListCommandsResultEvent.RemovalSuccess -> {
                news(MovieListNews.ShowBookmarkToast(event.movieTitle, false))
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
            is MovieListUiEvent.FilterMoviesClicked -> {
                news(MovieListNews.NavigateTo(MovieFilterFragment.newInstance(event.filterState)))
            }
            MovieListUiEvent.PaginationLoad -> {
                state { copy(isLoading = true) }
            }
            MovieListUiEvent.PaginationStopLoad -> {
                state { copy(isLoading = false) }
            }
            MovieListUiEvent.GetHistory -> {
                commands(MovieListCommand.GetHistory)
            }
            is MovieListUiEvent.SortedSearchCLicked -> {
                commands(MovieListCommand.SearchSorted(event.sortQuery, event.sortId, event.scope))
                state { copy(isLoading = true) }
            }
            is MovieListUiEvent.FavouritesClicked -> {
                news(MovieListNews.NavigateTo(MovieBookmarkFragment.getInstance()))
            }
            is MovieListUiEvent.AddToBookmarkClicked -> {
                commands(MovieListCommand.AddToBookmark(event.movieId, event.movieTitle))
            }
            is MovieListUiEvent.RemoveFromBookmarkClicked -> {
                commands(MovieListCommand.RemoveFromBookmark(event.movieId, event.movieTitle))
            }
        }
    }
}