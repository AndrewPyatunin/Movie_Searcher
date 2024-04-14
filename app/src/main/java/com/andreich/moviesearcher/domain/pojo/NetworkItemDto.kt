package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class NetworkItemDto(

    @SerializedName("name" ) var name : String? = null
)
