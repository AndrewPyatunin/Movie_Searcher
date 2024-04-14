package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.PosterDao
import com.andreich.moviesearcher.data.entity.PosterEntity
import javax.inject.Inject

class PosterDataSourceImpl @Inject constructor(
    private val posterDao: PosterDao
) : PosterDataSource {

    override fun getPosters(movieId: Int): PagingSource<Int, PosterEntity> {
        return posterDao.getPosters(movieId)
    }

    override suspend fun insertPosters(list: List<PosterEntity>) {
        posterDao.insertPosters(list)
    }
}