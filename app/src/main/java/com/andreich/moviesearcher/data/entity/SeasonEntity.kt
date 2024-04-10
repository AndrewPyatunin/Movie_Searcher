package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("season")
data class SeasonEntity(
    val movieId: Int,
    val number: Int,
    val episodesCount: Int,
    val episodes: List<EpisodeEntity> = arrayListOf(),
    val enName: String,
    val name: String,
    val airDate: String,
    val description: String,
    val enDescription: String,
    val url: String,
    val previewUrl: String,
    @PrimaryKey
    val id: String
)
