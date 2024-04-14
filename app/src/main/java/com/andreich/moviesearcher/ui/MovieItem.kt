package com.andreich.moviesearcher.ui

data class MovieItem(
    val id: Int,//+
    val name: String,//+
    val alternativeName: String = "",//+
    val type: String,//+
    val year: String,//+
    val ratingColor: Int,
//    val description: String = "",
    val rating: String,//+
    val genres: String,//+
    val countries: String,//+
    val previewUrl: String,//+
    val filmLength: String,//+
)
