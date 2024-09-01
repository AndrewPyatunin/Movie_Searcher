package com.andreich.moviesearcher.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    var id: Int,
    var name: String,
    var enName: String? = null,
    var photo: String? = null,
    var sex: String? = null,
    var growth: Int? = null,
    var birthday: String? = null,
    var death: String? = null,
    var age: Int? = null,
    var birthPlace: List<String> = arrayListOf(),
    var deathPlace: List<String> = arrayListOf(),
    var spouses: List<Spouses> = arrayListOf(),
    var countAwards: Int? = null,
    var profession: List<String> = arrayListOf(),
    var facts: List<String> = arrayListOf(),
    var movies: List<Movies> = arrayListOf(),
    var createdAt: String? = null,
    var updatedAt: String? = null
) : Parcelable