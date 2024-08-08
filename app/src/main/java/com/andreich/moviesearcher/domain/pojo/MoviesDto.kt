package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class MoviesDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("alternativeName") var alternativeName: String? = null,
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("general") var general: Boolean? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("enProfession") var enProfession: String? = null
)