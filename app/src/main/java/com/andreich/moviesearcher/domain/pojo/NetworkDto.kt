package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class NetworkDto(

    @SerializedName("items" ) var items : ArrayList<NetworkItemDto> = arrayListOf()
)
