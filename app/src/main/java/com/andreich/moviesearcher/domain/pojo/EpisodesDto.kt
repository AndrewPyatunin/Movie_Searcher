package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class EpisodesDto(

    @SerializedName("number") var number: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("enName") var enName: String? = null,
    @SerializedName("still") var episodePoster: PosterDto? = PosterDto(),
    @SerializedName("duration") var duration: Int? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("airDate") var airDate: String? = null,
    @SerializedName("enDescription") var enDescription: String? = null
)