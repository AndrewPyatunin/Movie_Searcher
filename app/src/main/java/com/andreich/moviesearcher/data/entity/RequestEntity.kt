package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("request")
data class RequestEntity(
    @PrimaryKey
    val id: Long,
    val moviesIds: List<Int>,
    val params: Map<String, String>
)
