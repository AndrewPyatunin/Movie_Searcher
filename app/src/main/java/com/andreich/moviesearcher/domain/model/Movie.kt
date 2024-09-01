package com.andreich.moviesearcher.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int = 0,
    val name: String,
    val alternativeName: String = "",
    val type: String,
    val year: Int,
    val slogan: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val ageRating: Int,
    val genres: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val url: String,
    val previewUrl: String,
    val actors: List<Person> = emptyList(),
    val votes: Int = 0,
    val network: List<String> = emptyList(),
    val seasonsAmount: Int? = null,
    val top250: Int = 0,
    val movieLength: Int? = null,
    val isSeries: Boolean,
    val seriesLength: Int? = null,
    val page: Int = 1,
    val requestId: String,
    val ratingImdb: Double = 0.0,
    val bookmark: Boolean
) : Parcelable
