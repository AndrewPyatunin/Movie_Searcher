package com.andreich.moviesearcher.presentation.movie_detail

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.model.Review
import com.andreich.moviesearcher.ui.ReviewItem
import kotlinx.coroutines.CoroutineScope

sealed interface MovieDetailEvent {


    sealed interface MovieDetailUiEvent : MovieDetailEvent {

        class LoadMovie(val scope: CoroutineScope, val movieId: Int) : MovieDetailUiEvent

        object BackPress : MovieDetailUiEvent
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