package com.andreich.moviesearcher.domain.model

data class Movie(
    val id: Int = 0,
    val name: String,
    val alternativeName: String = "",
    val type: MovieType = MovieType.Film(),
    val year: Int,
    val slogan: String,
    val description: String = "",
    val rating: Double = 0.0,
    val ageRating: Int,
    val genres: List<String>,
    val countries: List<String>,
    val url: String,
    val previewUrl: String,
    val actors: List<Person>,
    val votes: Int,
    val network: String,
    val seasonsAmount: Int? = null,
    val top250: Int = 0,
    val movieLength: Int?,
    val isSeries: Boolean,
    val seriesLength: Int?,
    val page: Int,
    val requestId: Long,
    val ratingImdb: Double
)
