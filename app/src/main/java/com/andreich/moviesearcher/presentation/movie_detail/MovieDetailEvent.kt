package com.andreich.moviesearcher.presentation.movie_detail

import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.*
import kotlinx.coroutines.CoroutineScope

sealed interface MovieDetailEvent {


    sealed interface MovieDetailUiEvent : MovieDetailEvent {

        class LoadMovie(val scope: CoroutineScope, val movieId: Int) : MovieDetailUiEvent

        object BackPress : MovieDetailUiEvent

        class NavigateTo(val fragment: Fragment) : MovieDetailUiEvent
    }

    sealed interface MovieDetailCommandsResultEvent : MovieDetailEvent {

        class DataIsReady(
            val movie: Movie,
            val reviews: PagingData<Review>,
            val actors: PagingData<Person>,
            val posters: PagingData<Poster>
        ) :
            MovieDetailCommandsResultEvent

        class LoadError(val message: String) : MovieDetailCommandsResultEvent
    }
}