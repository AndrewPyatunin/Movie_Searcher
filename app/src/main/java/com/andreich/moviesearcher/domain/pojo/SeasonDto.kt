package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class SeasonDto(

    @SerializedName("movieId") var movieId: Int? = null,
    @SerializedName("number") var number: Int? = null,
    @SerializedName("episodesCount") var episodesCount: Int? = null,
    @SerializedName("episodes") var episodes: ArrayList<EpisodeDto> = arrayListOf(),
    @SerializedName("enName") var enName: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("airDate") var airDate: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("enDescription") var enDescription: String? = null,
    @SerializedName("poster") var poster: PosterInfoDto? = PosterInfoDto(),
    @SerializedName("id") var id: String? = null
): RequestDto
