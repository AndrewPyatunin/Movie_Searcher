package com.andreich.moviesearcher.ui.state

import com.andreich.moviesearcher.ui.MovieItem

data class MovieBookmarkUiState(
    val progressVisibility: Boolean,
    val movies: List<MovieItem>
)