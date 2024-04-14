package com.andreich.moviesearcher.ui

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class MovieListUiState(
    val movies: PagingData<MovieItem>,
    val progressVisibility: Boolean,
    val listVisibility: Boolean
)