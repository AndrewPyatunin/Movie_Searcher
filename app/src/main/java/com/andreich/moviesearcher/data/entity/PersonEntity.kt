package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("actor")
data class PersonEntity(
    @PrimaryKey
    val id: Int,
    val photoUrl: String,
    val name: String,
    val enName: String,
    val profession: String,
    val enProfession: String,
    val description: String,
    val page: Int
)
