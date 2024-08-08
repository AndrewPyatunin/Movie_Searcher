package com.andreich.moviesearcher.di

import com.andreich.moviesearcher.data.entity.*
import com.andreich.moviesearcher.data.mapper.*
import com.andreich.moviesearcher.domain.pojo.*
import dagger.Binds
import dagger.Module

@Module
interface DtoModule {

    @Binds
    fun bindMovieDtoMapper(impl: MovieDtoToMovieMapper): MovieMapper<MovieDto, MovieEntity>

    @Binds
    fun bindReviewDtoMapper(impl: ReviewDtoToReviewEntityMapper): MovieMapper<ReviewDto, ReviewEntity>

    @Binds
    fun bindPosterDtoMapper(impl: PosterDtoToPosterEntityMapper): MovieMapper<PosterDto, PosterEntity>

    @Binds
    fun bindSeasonDtoMapper(impl: SeasonDtoToSeasonEntityMapper): MovieMapper<SeasonDto, SeasonEntity>

    @Binds
    fun bindPersonDtoMapper(impl: PersonDtoToPersonEntityMapper): MovieMapper<PersonDto, PersonEntity>

    @Binds
    fun bindActorDtoMapper(impl: ActorDtoToActorEntityMapper): MovieMapper<ActorDto, ActorEntity>

}