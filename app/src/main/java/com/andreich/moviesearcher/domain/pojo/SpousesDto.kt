package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class SpousesDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("divorced") val divorced: Boolean? = null,
    @SerializedName("children") val children: Int? = null,
    @SerializedName("relation") val relation: String? = null
)