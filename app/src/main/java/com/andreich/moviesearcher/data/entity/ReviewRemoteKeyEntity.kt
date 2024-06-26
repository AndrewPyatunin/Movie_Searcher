package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("review_remote_key")
data class ReviewRemoteKeyEntity(
    @PrimaryKey
    val id: Int,
    val valueId: Int,
    val prevKey: Int,
    val nextKey: Int,
    val currentPage: Int,
    val createdAt: Long = System.currentTimeMillis()
)
