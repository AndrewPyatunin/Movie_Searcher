package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreich.moviesearcher.domain.pojo.BirthPlaceDto
import com.andreich.moviesearcher.domain.pojo.FactsDto
import com.andreich.moviesearcher.domain.pojo.MoviesDto

@Entity("actor_detail")
data class ActorEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val enName: String? = null,
    val photo: String? = null,
    val sex: String? = null,
    val growth: Int? = null,
    val birthday: String? = null,
    val death: String? = null,
    val age: Int? = null,
    val birthPlace: List<String> = arrayListOf(),
    val deathPlace: List<String> = arrayListOf(),
    val spouses: List<SpousesEntity> = arrayListOf(),
    val countAwards: Int? = null,
    val profession: List<String> = arrayListOf(),
    val facts: List<String> = arrayListOf(),
    val movies: List<MoviesEntity> = arrayListOf(),
    val page: Int,
    val movieIds: List<Int>
)
