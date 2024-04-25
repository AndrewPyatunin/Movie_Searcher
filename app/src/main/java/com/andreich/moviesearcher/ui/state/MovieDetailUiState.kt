package com.andreich.moviesearcher.ui.state

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.model.Review
import com.andreich.moviesearcher.ui.MovieDetailItem

data class MovieDetailUiState(
    val imageProgressVisibility: Boolean,
    val movieDetailItem: MovieDetailItem?,
    val actors: PagingData<Person>?,
    val reviews: PagingData<Review>,
    val posters: PagingData<Poster>,
)