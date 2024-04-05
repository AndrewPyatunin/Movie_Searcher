package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("kp") var kp: Double? = null,
    @SerializedName("imdb") var imdb: Double? = null,
    @SerializedName("filmCritics") var filmCritics: Double? = null,
    @SerializedName("russianFilmCritics") var russianFilmCritics: Int? = null,
    @SerializedName("await") var await: String? = null
)
