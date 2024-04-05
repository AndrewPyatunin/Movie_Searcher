package com.andreich.moviesearcher.domain.model

sealed interface MovieType {

    @JvmInline
    value class Film(val type: String = "movie") : MovieType

    @JvmInline
    value class Series(val type: String = "tv-series") : MovieType

    @JvmInline
    value class Anime(val type: String = "anime") : MovieType

    @JvmInline
    value class AnimatedSeries(val type: String = "animated-series") : MovieType

    @JvmInline
    value class Cartoon(val type: String = "cartoon") : MovieType
}
