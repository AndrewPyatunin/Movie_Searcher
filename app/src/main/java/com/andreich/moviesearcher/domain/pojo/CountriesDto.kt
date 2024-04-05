package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class CountriesDto(

    @SerializedName("name") var name: String? = null
)
