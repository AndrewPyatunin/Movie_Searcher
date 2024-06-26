package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity("movie_remote_key")
data class MovieRemoteKeyEntity(
    @PrimaryKey
    val id: Int,
    val valueId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    val currentPage: Int,
    val createdAt: Long = System.currentTimeMillis()
)
