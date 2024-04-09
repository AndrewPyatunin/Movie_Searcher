package com.andreich.moviesearcher.domain.model

data class Poster(
    val url: String,
    val height: Int,
    val movieId: Int,
    val previewUrl: String,
    val type: String,
    val width: Int,
    val id: String
)
