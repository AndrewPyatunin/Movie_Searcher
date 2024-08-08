package com.andreich.moviesearcher.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Spouses(
    val id: Int,
    val name: String? = null,
    val divorced: Boolean? = null,
    val children: Int? = null,
    val relation: String? = null
) : Parcelable