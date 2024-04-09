package com.andreich.moviesearcher.domain.repo

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Poster
import kotlinx.coroutines.flow.Flow

interface PosterRepository {

    fun getPosters(movieId: Int, pageSize: Int): Flow<PagingData<Poster>>
}