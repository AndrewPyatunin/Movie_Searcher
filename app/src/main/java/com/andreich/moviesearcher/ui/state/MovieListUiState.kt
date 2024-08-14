package com.andreich.moviesearcher.ui.state

import androidx.paging.PagingData
import com.andreich.moviesearcher.ui.MovieItem

data class MovieListUiState(
    val movies: PagingData<MovieItem>?,
    val progressVisibility: Boolean,
    val listVisibility: Boolean,
    val query: String = "",
)