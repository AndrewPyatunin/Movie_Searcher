package com.andreich.moviesearcher.presentation.movie_filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieFilterState(
    val filters: Map<String, List<String>>,
    val positions: Map<String, Int>
) : Parcelable