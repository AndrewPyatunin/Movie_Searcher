package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("poster_remote_key")
data class PosterRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = System.currentTimeMillis(),
    val valueId: String,
    val prevKey: Int,
    val nextKey: Int,
    val currentPage: Int,
    val createdAt: Long = System.currentTimeMillis()
)