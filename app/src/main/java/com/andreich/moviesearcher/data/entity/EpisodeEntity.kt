package com.andreich.moviesearcher.data.entity

data class EpisodeEntity(
    var number: Int,
    var name: String,
    var enName: String,
    val url: String,
    val previewUrl: String,
    var duration: Int,
    var date: String,
    var description: String,
    var airDate: String,
    var enDescription: String
)
