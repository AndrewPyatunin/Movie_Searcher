package com.andreich.moviesearcher.domain.model

data class Movie(
    val id: Int = 0,
    val name: String,
    val alternativeName: String = "",
    val type: MovieType = MovieType.Film(),
    val year: Int,
    val description: String = "",
    val rating: List<Double>,
    val ageRating: Int,
    val genres: List<String>,
    val countries: List<String>,
    val url: String,
    val previewUrl: String,
    val actors: List<Person>,
    val network: String,
    val seasonsInfo: List<TvSeriesSeason>
)
