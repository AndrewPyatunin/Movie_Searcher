package com.andreich.moviesearcher.presentation.movie_list

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.MovieSearchHistory
import com.andreich.moviesearcher.ui.MovieItem
import kotlinx.coroutines.CoroutineScope

sealed interface MovieListEvent {

    sealed interface MovieListUiEvent : MovieListEvent {

        class LoadData(val scope: CoroutineScope) : MovieListUiEvent

        class SearchClicked(val name: String, val scope: CoroutineScope, val requestId: String) :
            MovieListUiEvent

        object FilterMoviesClicked : MovieListUiEvent

        object PaginationLoad : MovieListUiEvent

        object PaginationStopLoad : MovieListUiEvent

        class FilterSearchClicked(val query: Map<String, List<String>>, val scope: CoroutineScope) : MovieListUiEvent

        class MovieItemClicked(val movie: MovieItem) : MovieListUiEvent

        object GetHistory : MovieListUiEvent
    }

    sealed interface MovieListCommandsResultEvent : MovieListEvent {

        class DataIsReady(val movies: PagingData<Movie>?) : MovieListCommandsResultEvent

        class LoadError(val message: String) : MovieListCommandsResultEvent

        class SearchHistoryIsReady(val history: List<MovieSearchHistory>) : MovieListCommandsResultEvent
    }
}