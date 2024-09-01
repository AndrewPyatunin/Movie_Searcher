package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class BirthPlaceDto(

    @SerializedName("value") var value: String? = null
)