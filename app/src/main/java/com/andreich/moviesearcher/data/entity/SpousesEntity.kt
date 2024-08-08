package com.andreich.moviesearcher.data.entity

data class SpousesEntity(
    val id: Int,
    val name: String,
    val divorced: Boolean? = null,
    val children: Int? = null,
    val relation: String? = null
)