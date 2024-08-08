package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class ActorDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("enName") var enName: String? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("sex") var sex: String? = null,
    @SerializedName("growth") var growth: Int? = null,
    @SerializedName("birthday") var birthday: String? = null,
    @SerializedName("death") var death: String? = null,
    @SerializedName("age") var age: Int? = null,
    @SerializedName("birthPlace") var birthPlace: ArrayList<BirthPlaceDto>? = arrayListOf(),
    @SerializedName("deathPlace") var deathPlace: ArrayList<String>? = arrayListOf(),
    @SerializedName("spouses") var spouses: ArrayList<SpousesDto>? = arrayListOf(),
    @SerializedName("countAwards") var countAwards: Int? = null,
    @SerializedName("profession") var profession: ArrayList<ProfessionDto>? = arrayListOf(),
    @SerializedName("facts") var facts: ArrayList<FactsDto>? = arrayListOf(),
    @SerializedName("movies") var movies: ArrayList<MoviesDto>? = arrayListOf(),
    @SerializedName("createdAt") var createdAt: String? = null,
    @SerializedName("updatedAt") var updatedAt: String? = null
) : RequestDto