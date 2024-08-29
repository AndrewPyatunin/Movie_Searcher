package com.andreich.moviesearcher.data.repo

import android.util.Log
import com.andreich.moviesearcher.data.datasource.local.SeasonDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.SeasonEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.domain.model.Season
import com.andreich.moviesearcher.domain.pojo.SeasonDto
import com.andreich.moviesearcher.domain.repo.SeasonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeasonRepositoryImpl @Inject constructor(
    private val seasonDataSource: SeasonDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val seasonDtoToSeasonEntityMapper: MovieMapper<SeasonDto, SeasonEntity>,
    private val seasonEntityToSeasonMapper: EntityToModelMapper<SeasonEntity, Season>
) : SeasonRepository {

    override suspend fun getSeasons(movieId: Int): Flow<List<Season>> {
        return remoteDataSource.getSeasons(movieId = movieId).let {
            it.docs.map { season ->
                Log.d("MOVIE_DETAIL_REPO", season.toString())
                seasonDtoToSeasonEntityMapper.map(season, it.page ?: 1, "")
            }.let {
                seasonDataSource.insertSeasons(it)
            }.let {
                seasonDataSource.getSeasons(movieId).map {
                    it.map {
                        seasonEntityToSeasonMapper.map(it)
                    }
                }
            }
        }
    }
}