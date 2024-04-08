package com.andreich.moviesearcher.data.entity

import androidx.room.Entity

@Entity("actor")
data class PersonEntity(
    val id: Int,
    val photoUrl: String?,
    val name: String?,
    val enName: String?,
    val profession: String?,
    val enProfession: String?,
    val page: Int
)
