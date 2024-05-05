package com.andreich.moviesearcher.presentation.movie_detail

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.model.Review
import com.andreich.moviesearcher.ui.ReviewItem

data class MovieDetailState(
    val isLoading: Boolean,
    val reviews: PagingData<Review>,
    val posters: PagingData<Poster>,
    val persons: PagingData<Person>,
    val movie: Movie?
)