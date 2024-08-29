package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class SeasonDto(

    @SerializedName("movieId"       ) var movieId       : Int?                = null,
    @SerializedName("number"        ) var number        : Int?                = null,
    @SerializedName("airDate"       ) var airDate       : String?             = null,
    @SerializedName("createdAt"     ) var createdAt     : String?             = null,
    @SerializedName("enName"        ) var enName        : String?             = null,
    @SerializedName("episodes"      ) var episodes      : ArrayList<EpisodeDto> = arrayListOf(),
    @SerializedName("episodesCount" ) var episodesCount : Int?                = null,
    @SerializedName("name"          ) var name          : String?             = null,
    @SerializedName("source"        ) var source        : String?             = null,
    @SerializedName("updatedAt"     ) var updatedAt     : String?             = null,
    @SerializedName("id"            ) var id            : String?             = null
): RequestDto
