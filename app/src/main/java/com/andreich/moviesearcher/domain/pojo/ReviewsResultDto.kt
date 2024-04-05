package com.andreich.moviesearcher.domain.pojo

import com.google.gson.annotations.SerializedName

data class ReviewsResultDto(

    @SerializedName("docs") var docs: ArrayList<ReviewDto> = arrayListOf(),
    @SerializedName("total") var total: Int? = null,
    @SerializedName("limit") var limit: Int? = null,
    @SerializedName("page") var page: Int? = null,
    @SerializedName("pages") var pages: Int? = null
)
