package com.andreich.moviesearcher.data.datasource.local

import com.andreich.moviesearcher.data.entity.*
import kotlinx.coroutines.flow.Flow


interface SeasonDataSource {

    fun getSeasons(movieId: Int): Flow<List<SeasonEntity>>

    suspend fun insertSeasons(list: List<SeasonEntity>)
}