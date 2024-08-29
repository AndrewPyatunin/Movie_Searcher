package com.andreich.moviesearcher.presentation.movie_detail

import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.*
import kotlinx.coroutines.CoroutineScope

sealed interface MovieDetailEvent {


    sealed interface MovieDetailUiEvent : MovieDetailEvent {

        class LoadMovie(val scope: CoroutineScope, val movieId: Int) : MovieDetailUiEvent

        class LoadSeasons(val movieId: Int) : MovieDetailUiEvent

        class AddToBookmark(val movieId: Int, val isBookmark: Boolean) : MovieDetailUiEvent

        object BackPress : MovieDetailUiEvent

        class NavigateTo(val fragment: Fragment) : MovieDetailUiEvent
    }

    sealed interface MovieDetailCommandsResultEvent : MovieDetailEvent {

        class DataIsReady(
            val movie: Movie,
            val reviews: PagingData<Review>,
            val actors: PagingData<Person>,
            val posters: List<Poster>,
            val isBookmark: Boolean
        ) :
            MovieDetailCommandsResultEvent

        class SeasonIsReady(
            val seasons: List<Season>
        ) : MovieDetailCommandsResultEvent

        class LoadError(val message: String) : MovieDetailCommandsResultEvent

        class Success(val isBookmark: Boolean) : MovieDetailCommandsResultEvent
    }
}