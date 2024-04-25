package com.andreich.moviesearcher.presentation.movie_list

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.ui.MovieItem
import kotlinx.coroutines.CoroutineScope

sealed interface MovieListEvent {

    sealed interface MovieListUiEvent : MovieListEvent {

        class LoadData(val scope: CoroutineScope) : MovieListUiEvent

        class SearchClicked(val name: String, val scope: CoroutineScope, val requestId: String) :
            MovieListUiEvent

        class FilterSearchClicked(val query: String, val scope: CoroutineScope) : MovieListUiEvent

        class MovieItemClicked(val movie: MovieItem) : MovieListUiEvent
    }

    sealed interface MovieListCommandsResultEvent : MovieListEvent {

        class DataIsReady(val movies: PagingData<Movie>?) : MovieListCommandsResultEvent

        class LoadError(val message: String) : MovieListCommandsResultEvent

    }
}