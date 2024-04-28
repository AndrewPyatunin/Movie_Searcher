package com.andreich.moviesearcher.presentation

import android.util.Log
import com.andreich.moviesearcher.presentation.movie_list.MovieListEvent
import com.andreich.moviesearcher.presentation.movie_list.MovieListState
import javax.inject.Inject

class AnalyticsTracker @Inject constructor() {

    operator fun invoke(state: MovieListState, event: MovieListEvent) {
        when (event) {
            is MovieListEvent.MovieListCommandsResultEvent.DataIsReady -> {
//                trackUiEvent(state, event)
            }
            is MovieListEvent.MovieListCommandsResultEvent.LoadError -> {
                Unit
            }
            is MovieListEvent.MovieListUiEvent.FilterSearchClicked -> {
                trackUiEvent(state, event)
            }
            is MovieListEvent.MovieListUiEvent.LoadData -> {
//                trackUiEvent(state, event)
            }
            is MovieListEvent.MovieListUiEvent.MovieItemClicked -> {
                trackUiEvent(state, event)
            }
            is MovieListEvent.MovieListUiEvent.SearchClicked -> {
                trackUiEvent(state, event)
            }
            MovieListEvent.MovieListUiEvent.FilterMoviesClicked -> {

            }
            MovieListEvent.MovieListUiEvent.PaginationLoad -> {

            }
            MovieListEvent.MovieListUiEvent.PaginationStopLoad -> {

            }
        }
    }

    private fun trackUiEvent(state: MovieListState, event: MovieListEvent.MovieListUiEvent) {
        when (event) {
            is MovieListEvent.MovieListUiEvent.FilterSearchClicked -> {

            }
            is MovieListEvent.MovieListUiEvent.LoadData -> {
                Log.d("MovieAnalytics", "Load data")
            }
            is MovieListEvent.MovieListUiEvent.MovieItemClicked -> {

            }
            is MovieListEvent.MovieListUiEvent.SearchClicked -> {
                Log.d("MovieAnalytics", "Load data ${state.movies}")
            }
            MovieListEvent.MovieListUiEvent.FilterMoviesClicked -> {

            }
            MovieListEvent.MovieListUiEvent.PaginationLoad -> {

            }
            MovieListEvent.MovieListUiEvent.PaginationStopLoad -> {

            }
        }
    }
}