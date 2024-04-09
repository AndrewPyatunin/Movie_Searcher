package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class TvSeriesDto(

    @SerializedName("movieId") var movieId: Int? = null,
    @SerializedName("number") var number: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("enDescription") var enDescription: String? = null,
    @SerializedName("enName") var enName: String? = null,
    @SerializedName("episodes") var episodes: ArrayList<EpisodeDto> = arrayListOf(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("poster") var poster: PosterInfoDto? = PosterInfoDto(),
    @SerializedName("id") var id: String? = null
)
