package com.andreich.moviesearcher.presentation.movie_bookmark

import com.andreich.moviesearcher.domain.model.Movie

data class MovieBookmarkState(
    val isLoading: Boolean,
    val movies: List<Movie>
)