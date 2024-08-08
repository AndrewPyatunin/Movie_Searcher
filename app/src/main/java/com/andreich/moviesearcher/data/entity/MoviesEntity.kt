package com.andreich.moviesearcher.data.entity

data class MoviesEntity(
    var id: Int,
    var name: String,
    var alternativeName: String? = null,
    var rating: Double? = null,
    var general: Boolean? = null,
    var description: String? = null,
    var enProfession: String? = null
)