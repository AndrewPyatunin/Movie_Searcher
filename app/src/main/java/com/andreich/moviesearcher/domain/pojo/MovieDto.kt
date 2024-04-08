package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class MovieDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("rating") var rating: RatingDto? = RatingDto(),
    @SerializedName("movieLength") var movieLength: Int? = null,
    @SerializedName("isSeries") var isSeries: Boolean? = null,
    @SerializedName("seriesLength") var seriesLength: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("votes") var votes: VotesDto? = VotesDto(),
    @SerializedName("slogan") var slogan: String? = null,
    @SerializedName("year") var year: Int? = null,
    @SerializedName("poster") var poster: PosterDto? = PosterDto(),
    @SerializedName("genres") var genres: ArrayList<GenresDto> = arrayListOf(),
    @SerializedName("countries") var countries: ArrayList<CountriesDto> = arrayListOf(),
    @SerializedName("seasonsInfo") var seasonsInfo: ArrayList<SeasonsInfoDto> = arrayListOf(),
    @SerializedName("persons") var persons: ArrayList<PersonsDto> = arrayListOf(),
    @SerializedName("alternativeName") var alternativeName: String? = null,
    @SerializedName("networks") var networks: String? = null,
    @SerializedName("ageRating") var ageRating: Int? = null,
    @SerializedName("logo") var logo: LogoDto? = LogoDto(),
    @SerializedName("top250") var top250: Int? = null
): RequestDto
