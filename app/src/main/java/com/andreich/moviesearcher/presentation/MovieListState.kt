package com.andreich.moviesearcher.presentation

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean,
    val movies: PagingData<Movie>
)
