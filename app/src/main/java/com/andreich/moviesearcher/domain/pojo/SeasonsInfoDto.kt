package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class SeasonsInfoDto(

    @SerializedName("number") var number: Int? = null,
    @SerializedName("episodesCount") var episodesCount: Int? = null
)
