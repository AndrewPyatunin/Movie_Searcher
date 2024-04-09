package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class PosterDto(

    @SerializedName("url") var url: String? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("movieId") var movieId: Int? = null,
    @SerializedName("previewUrl") var previewUrl: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("id") var id: String? = null
): RequestDto
