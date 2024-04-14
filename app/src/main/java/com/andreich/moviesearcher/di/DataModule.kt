package com.andreich.moviesearcher.di

import com.andreich.moviesearcher.data.datasource.local.*
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSourceImpl
import com.andreich.moviesearcher.data.entity.EpisodeEntity
import com.andreich.moviesearcher.data.mapper.DtoMapper
import com.andreich.moviesearcher.data.mapper.EpisodeDtoToEpisodeEntityMapper
import com.andreich.moviesearcher.domain.pojo.EpisodeDto
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindMovieDatasource(impl: MovieDataSourceImpl): MovieDataSource

    @Binds
    fun bindPersonDataSource(impl: PersonDataSourceImpl): PersonDataSource

    @Binds
    fun bindReviewDataSource(impl: ReviewDataSourceImpl): ReviewDataSource

    @Binds
    fun bindSeasonDataSource(impl: SeasonDataSourceImpl): SeasonDataSource

    @Binds
    fun bindPosterDataSource(impl: PosterDataSourceImpl): PosterDataSource

    @Binds
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindEpisodeDtoMapper(impl: EpisodeDtoToEpisodeEntityMapper): DtoMapper<EpisodeDto, EpisodeEntity>
}