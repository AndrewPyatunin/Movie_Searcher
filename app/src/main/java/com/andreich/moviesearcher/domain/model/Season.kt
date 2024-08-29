package com.andreich.moviesearcher.domain.model

data class Season(
    val id: String,
    val movieId: Int,
    val number: Int,
    val airDate: String,
    val enName: String,
    val episodes: List<Episode>,
    val episodesCount: Int,
    val name: String,
    val source: String,
)
