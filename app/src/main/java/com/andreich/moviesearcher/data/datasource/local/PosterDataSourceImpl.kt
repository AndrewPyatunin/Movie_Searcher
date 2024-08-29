package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.PosterDao
import com.andreich.moviesearcher.data.entity.PosterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PosterDataSourceImpl @Inject constructor(
    private val posterDao: PosterDao
) : PosterDataSource {

    override fun getPosters(movieId: Int): PagingSource<Int, PosterEntity> {
        return posterDao.getPosters(movieId)
    }

    override fun getPostersDetail(movieId: Int): Flow<List<PosterEntity>> {
        return posterDao.getPostersDetail(movieId)
    }

    override suspend fun insertPosters(list: List<PosterEntity>) {
        posterDao.insertPosters(list)
    }
}