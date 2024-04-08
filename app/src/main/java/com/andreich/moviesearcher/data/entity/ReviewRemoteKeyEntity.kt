package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity("review_remote_key")
data class ReviewRemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = System.currentTimeMillis(),
    val valueId: Int,
    val valueType: KClass<out Entities>,
    val prevKey: Int?,
    val nextKey: Int?,
    val currentPage: Int,
    val createdAt: Long = System.currentTimeMillis()
)
