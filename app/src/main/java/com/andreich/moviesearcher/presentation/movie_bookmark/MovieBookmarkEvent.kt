package com.andreich.moviesearcher.presentation.movie_bookmark

import androidx.fragment.app.Fragment
import com.andreich.moviesearcher.domain.model.Movie

sealed interface MovieBookmarkEvent {

    sealed interface MovieBookmarkUiEvent : MovieBookmarkEvent {

        class NavigateTo(val fragment: Fragment) : MovieBookmarkUiEvent

        class RemoveBookmark(val movieId: Int, val movieTitle: String) : MovieBookmarkUiEvent

        object LoadMovies : MovieBookmarkUiEvent
    }

    sealed interface MovieBookmarkCommandResultEvent : MovieBookmarkEvent {

        class LoadError(val message: String) : MovieBookmarkCommandResultEvent

        class DataIsReady(val movies: List<Movie>) : MovieBookmarkCommandResultEvent

        class RemoveSuccessful(val movieTitle: String) : MovieBookmarkCommandResultEvent
    }
}