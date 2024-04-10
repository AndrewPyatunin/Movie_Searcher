package com.andreich.moviesearcher.domain.model

import com.andreich.moviesearcher.data.entity.EpisodeEntity

data class Season(
    val movieId: Int?,
    val number: Int?,
    val episodesCount: Int?,
    val episodes: List<EpisodeEntity> = arrayListOf(),
    val enName: String?,
    val name: String?,
    val airDate: String?,
    val description: String?,
    val enDescription: String?,
    val url: String?,
    val previewUrl: String?,
    val id: String
)
