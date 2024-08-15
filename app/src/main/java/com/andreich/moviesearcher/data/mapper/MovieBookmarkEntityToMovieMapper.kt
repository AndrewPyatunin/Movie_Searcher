package com.andreich.moviesearcher.data.mapper

import com.andreich.moviesearcher.data.entity.BookmarkMovieEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Person
import javax.inject.Inject

class MovieBookmarkEntityToMovieMapper @Inject constructor(
    private val personMapper: EntityToModelMapper<PersonEntity, Person>
): EntityToModelMapper<BookmarkMovieEntity, Movie> {

    override fun map(from: BookmarkMovieEntity): Movie {
        return with(from) {
            Movie(
                id = id ?: 0,
                name = name ?: "",
                alternativeName = alternativeName ?: "",
                type = type,
                year = year,
                slogan = slogan.toString(),
                description = description ?: "",
                rating = rating ?: 0.0,
                ratingImdb = ratingImdb ?: 0.0,
                ageRating = ageRating ?: 0,
                genres = genres,
                countries = countries,
                url = url.toString(),
                previewUrl = previewUrl.toString(),
                actors = actors.map {
                    personMapper.map(it)
                },
                votes = votes ?: 0,
                network = network,
                seasonsAmount = seasonsAmount,
                top250 = top250 ?: 0,
                movieLength = movieLength,
                isSeries = isSeries ?: false,
                seriesLength = seriesLength,
                page = 1,
                requestId = requestId,
                bookmark = true
            )
        }
    }
}