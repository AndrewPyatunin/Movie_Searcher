package com.andreich.moviesearcher.data.entity

import androidx.room.Entity

@Entity("request")
data class RequestEntity(
    val id: Long,
    val moviesIds: List<Int>,
    val params: Map<String, String>?
)
