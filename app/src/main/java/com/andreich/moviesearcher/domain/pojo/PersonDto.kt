package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class PersonDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("photo") var photo: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("enName") var enName: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("profession") var profession: String? = null,
    @SerializedName("enProfession") var enProfession: String? = null
): RequestDto
