package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity("remote_key")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val valueId: Int,
    val valueType: KClass<Entities>,
    val prevKey: Int?,
    val nextKey: Int?,
    val currentPage: Int,
    val createdAt: Long = System.currentTimeMillis()
)
