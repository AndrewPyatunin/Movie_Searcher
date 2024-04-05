package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("previewUrl")
    var previewUrl: String? = null
)
