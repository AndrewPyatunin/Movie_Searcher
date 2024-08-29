package com.andreich.moviesearcher.presentation.movie_detail

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.*

data class MovieDetailState(
    val isBookmark: Boolean,
    val isLoading: Boolean,
    val reviews: PagingData<Review>,
    val posters: List<Poster>,
    val persons: PagingData<Person>,
    val movie: Movie?,
    val seasons: List<Season>
)