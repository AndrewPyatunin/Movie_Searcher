package com.andreich.moviesearcher.ui.state

import androidx.paging.PagingData
import com.andreich.moviesearcher.ui.MovieItem

data class MovieListStateUi(
    val filters: String,
    val movies: PagingData<MovieItem>,
    val isLoading: Boolean
)