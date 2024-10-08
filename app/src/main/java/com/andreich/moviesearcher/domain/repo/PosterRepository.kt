package com.andreich.moviesearcher.domain.repo

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Poster
import kotlinx.coroutines.flow.Flow

interface PosterRepository {

    fun getPosters(movieId: Int, pageSize: Int, requestId: String): Flow<PagingData<Poster>>

    suspend fun getPostersDetail(movieId: Int): Flow<List<Poster>>
}