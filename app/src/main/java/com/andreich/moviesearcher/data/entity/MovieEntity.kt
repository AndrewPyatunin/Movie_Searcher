package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("movie")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val alternativeName: String,
    val type: String,
    val year: Int,
    val slogan: String,
    val description: String,
    val rating: Double,
    val ratingImdb: Double,
    val ageRating: Int,
    val genres: List<String>,
    val countries: List<String>,
    val url: String,
    val previewUrl: String,
    val actors: List<PersonEntity>,
    val votes: Int?,
    val network: List<String>,
    val seasonsAmount: Int?,
    val top250: Int,
    val movieLength: Int,
    val isSeries: Boolean,
    val seriesLength: Int,
    val page: Int,
    val requestId: String
)
