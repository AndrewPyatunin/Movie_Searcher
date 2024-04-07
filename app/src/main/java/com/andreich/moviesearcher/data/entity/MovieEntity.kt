package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreich.moviesearcher.domain.model.MovieType

@Entity("movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val alternativeName: String = "",
    val type: MovieType = MovieType.Film(),
    val year: Int,
    val slogan: String?,
    val description: String = "",
    val rating: Double = 0.0,
    val ratingImdb: Double?,
    val ageRating: Int,
    val genres: List<String>,
    val countries: List<String>,
    val url: String?,
    val previewUrl: String?,
    val actors: List<PersonEntity>,
    val votes: Int?,
    val network: String,
    val seasonsAmount: Int?,
    val top250: Int?,
    val movieLength: Int?,
    val isSeries: Boolean,
    val seriesLength: Int?,
    val page: Int?,
    val requestId: Long
)
