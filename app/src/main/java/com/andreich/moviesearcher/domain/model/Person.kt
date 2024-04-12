package com.andreich.moviesearcher.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Int?,
    val photoUrl: String?,
    val name: String?,
    val enName: String?,
    val profession: String?,
    val enProfession: String?
) : Parcelable
