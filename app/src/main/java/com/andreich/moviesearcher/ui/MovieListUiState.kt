package com.andreich.moviesearcher.ui

import androidx.paging.PagingData

data class MovieListUiState(
    val movies: PagingData<MovieItem>?,
    val progressVisibility: Boolean,
    val listVisibility: Boolean,
    val query: String = "",
)