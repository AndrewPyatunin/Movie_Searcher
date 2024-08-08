package com.andreich.moviesearcher.ui

import com.andreich.moviesearcher.domain.model.Movies

data class ActorDetailItem(
    val id: Int,
    val name: String,
    val enName: String? = null,
    val photo: String? = null,
    val sex: String? = null,
    val growth: String? = null,
    val birthday: String? = null,
    val death: String? = null,
    val age: String? = null,
    val birthPlace: String? = null,
    val deathPlace: String? = null,
    val spouses: String? = null,
    val countAwards: Int? = null,
    val profession: String,
    val facts: String,
    val movies: List<Movies> = arrayListOf(),
)
