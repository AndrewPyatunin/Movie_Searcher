package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.domain.model.MovieType
import com.andreich.moviesearcher.domain.pojo.MovieDto
import com.andreich.moviesearcher.domain.pojo.PersonsDto

class MovieDtoToMovieMapper(
    private val personMapper: DtoMapper<PersonsDto, PersonEntity>,
) : MovieMapper<MovieDto, MovieEntity> {

    override fun map(fromDto: MovieDto, item: Int, requestId: Long): MovieEntity {
        return MovieEntity(
            id = fromDto.id ?: 0,
            name = fromDto.name ?: "",
            alternativeName = fromDto.alternativeName ?: "",
            type = (fromDto.type ?: MovieType.Film()) as MovieType,
            year = fromDto.year ?: 0,
            slogan = fromDto.slogan,
            description = fromDto.description ?: "",
            rating = fromDto.rating?.kp ?: 0.0,
            ratingImdb = fromDto.rating?.imdb,
            ageRating = fromDto.ageRating ?: 0,
            genres = fromDto.genres.map {
                it.name.toString()
            },
            countries = fromDto.countries.map {
                it.name.toString()
            },
            url = fromDto.poster?.url,
            previewUrl = fromDto.poster?.previewUrl,
            actors = fromDto.persons.map {
                personMapper.map(it)
            },
            votes = fromDto.votes?.kp,
            network = fromDto.networks ?: "",
            seasonsAmount = fromDto.seasonsInfo.size,
            top250 = fromDto.top250 ?: 0,
            movieLength = fromDto.movieLength,
            isSeries = fromDto.isSeries ?: false,
            seriesLength = fromDto.seriesLength,
            page = 1,
            requestId = requestId
        )
    }
}