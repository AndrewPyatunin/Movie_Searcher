package com.andreich.moviesearcher.data.entity

sealed interface Entities {

    object Movies: Entities

    object Seasons: Entities

    object Posters: Entities

    object Reviews: Entities

    object Actors: Entities
}