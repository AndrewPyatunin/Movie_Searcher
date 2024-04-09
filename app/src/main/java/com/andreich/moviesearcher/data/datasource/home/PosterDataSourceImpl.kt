package com.andreich.moviesearcher.data.datasource.home

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.PosterDao
import com.andreich.moviesearcher.data.entity.PosterEntity
import javax.inject.Inject

class PosterDataSourceImpl @Inject constructor(
    private val posterDao: PosterDao
) : PosterDataSource {

    override fun getPosters(): PagingSource<Int, PosterEntity> {
        return posterDao.getPosters()
    }

    override suspend fun insertPosters(list: List<PosterEntity>) {
        posterDao.insertPosters(list)
    }
}