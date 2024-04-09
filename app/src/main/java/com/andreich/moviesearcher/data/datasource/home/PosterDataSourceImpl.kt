package com.andreich.moviesearcher.data.datasource.home

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.PosterDao
import com.andreich.moviesearcher.data.entity.PosterDetailEntity

class PosterDataSourceImpl(
    private val posterDao: PosterDao
) : PosterDataSource {

    override fun getPosters(): PagingSource<Int, PosterDetailEntity> {
        return posterDao.getPosters()
    }

    override suspend fun insertPosters(list: List<PosterDetailEntity>) {
        posterDao.insertPosters(list)
    }
}