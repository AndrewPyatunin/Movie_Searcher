package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.domain.pojo.MovieDto
import com.andreich.moviesearcher.domain.pojo.PersonDto
import javax.inject.Inject

class MovieDtoToMovieMapper @Inject constructor(
    private val personMapper: MovieMapper<PersonDto, PersonEntity>,
) : MovieMapper<MovieDto, MovieEntity> {

    override fun map(fromDto: MovieDto, item: Int, requestId: String): MovieEntity {
        return MovieEntity(
            id = fromDto.id ?: 0,
            name = fromDto.name ?: "",
            alternativeName = fromDto.alternativeName ?: "",
            type = fromDto.type ?: "",
            year = fromDto.year ?: 0,
            slogan = fromDto.slogan ?: "",
            description = fromDto.description ?: "",
            rating = fromDto.rating?.kp ?: 0.0,
            ratingImdb = fromDto.rating?.imdb ?: 0.0,
            ageRating = fromDto.ageRating ?: 0,
            genres = fromDto.genres.map {
                it.name ?: ""
            },
            countries = fromDto.countries.map {
                it.name.toString()
            },
            firstCountry = if (fromDto.countries.size > 0) fromDto.countries[0].name else null,
            url = fromDto.poster?.url ?: "",
            previewUrl = fromDto.poster?.previewUrl ?: "",
            actors = fromDto.persons.map {
                personMapper.map(it, item, requestId)
            },
            votes = fromDto.votes?.kp,
            network = fromDto.networks?.items?.map {
                it.name ?: ""
            } ?: emptyList(),
            seasonsAmount = fromDto.seasonsInfo?.size,
            top250 = fromDto.top250 ?: 0,
            movieLength = fromDto.movieLength ?: -1,
            isSeries = fromDto.isSeries ?: false,
            seriesLength = fromDto.seriesLength ?: -1,
            page = item,
            requestId = requestId
        )
    }
}