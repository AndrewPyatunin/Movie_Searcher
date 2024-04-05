package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class MovieDto(

    @SerializedName("ratingDto") var ratingDto: RatingDto? = RatingDto(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("slogan") var slogan: String? = null,
    @SerializedName("year") var year: Int? = null,
    @SerializedName("posterDto") var posterDto: PosterDto? = PosterDto(),
    @SerializedName("genres") var genres: ArrayList<GenresDto> = arrayListOf(),
    @SerializedName("countries") var countries: ArrayList<CountriesDto> = arrayListOf(),
    @SerializedName("seasonsInfo") var seasonsInfo: ArrayList<String> = arrayListOf(),
    @SerializedName("people") var people: ArrayList<PersonsDto> = arrayListOf(),
    @SerializedName("alternativeName") var alternativeName: String? = null,
    @SerializedName("ageRating") var ageRating: Int? = null,
    @SerializedName("logoDto") var logoDto: LogoDto? = LogoDto(),
    @SerializedName("networks") var networks: String? = null
)
