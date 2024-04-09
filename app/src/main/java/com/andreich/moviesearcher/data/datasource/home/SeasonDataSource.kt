package com.andreich.moviesearcher.data.datasource.home

import com.andreich.moviesearcher.data.entity.*
import kotlinx.coroutines.flow.Flow


interface SeasonDataSource {

    fun getSeasons(): Flow<SeasonEntity>

    suspend fun insertSeasons(list: List<SeasonEntity>)
}