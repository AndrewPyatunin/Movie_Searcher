package com.andreich.moviesearcher.ui

import com.andreich.moviesearcher.domain.model.Person

data class MovieDetailItem(
    val id: Int,
    val title: String,
    val description: String,
    val slogan: String,
    val genres: String,
    val countries: String,
    val url: String,
    val actors: List<Person>,
)