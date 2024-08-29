package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class EpisodeDto(

    @SerializedName("number"      ) var number      : Int?    = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("enName"      ) var enName      : String? = null,
    @SerializedName("airDate"     ) var airDate     : String? = null,
    @SerializedName("description" ) var description : String? = null
)