package com.andreich.moviesearcher.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movies(
    val id: Int,
    val name: String? = null,
    val alternativeName: String? = null,
    val rating: Double? = null,
    val general: Boolean? = null,
    val description: String? = null,
    val enProfession: String? = null,
    val ratingColor: Int? = null
) : Parcelable
