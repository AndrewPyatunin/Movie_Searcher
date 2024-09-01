package com.andreich.moviesearcher.ui.state

data class MovieFilterUiState(
    val networkPosition: Int,
    val countryPosition: Int,
    val genresPosition: Int,
    val movieTypePosition: Int,
    val yearStart: String,
    val yearEnd: String,
    val rating: String
)