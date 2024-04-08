package com.andreich.moviesearcher.domain.model

data class Request (
    val id: Long,
    val moviesIds: List<Int>,
    val params: Map<String, String>?
    )
