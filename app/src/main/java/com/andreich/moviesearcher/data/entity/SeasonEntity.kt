package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreich.moviesearcher.domain.pojo.EpisodeDto

@Entity("season")
data class SeasonEntity(
    @PrimaryKey
    val id: String,
    val movieId: Int,
    val number: Int,
    val airDate: String,
    val enName: String,
    val episodes: List<EpisodeEntity>,
    val episodesCount: Int,
    val name: String,
    val source: String,
)
