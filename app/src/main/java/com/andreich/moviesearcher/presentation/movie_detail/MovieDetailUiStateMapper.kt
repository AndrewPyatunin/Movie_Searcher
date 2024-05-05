package com.andreich.moviesearcher.presentation.movie_detail

import androidx.paging.map
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.presentation.HtmlStringFormatter
import com.andreich.moviesearcher.ui.MovieDetailItem
import com.andreich.moviesearcher.ui.ReviewItem
import com.andreich.moviesearcher.ui.state.MovieDetailUiState
import ru.tinkoff.kotea.android.ui.ResourcesProvider
import ru.tinkoff.kotea.android.ui.UiStateMapper

class MovieDetailUiStateMapper(
    private val htmlStringFormatter: HtmlStringFormatter
) : UiStateMapper<MovieDetailState, MovieDetailUiState> {

    override fun ResourcesProvider.map(state: MovieDetailState): MovieDetailUiState {
        return MovieDetailUiState(
            imageProgressVisibility = state.isLoading,
            movieDetailItem = state.movie?.mapMovieToMovieDetailItem(),
            actors = state.persons,
            reviews = state.reviews.map {
                ReviewItem(
                    it.id,
                    it.movieId,
                    it.title,
                    it.type,
                    htmlStringFormatter(it.review),
                    it.date,
                    it.author
                )
            },
            posters = state.posters
        )
    }

    private fun Movie.mapMovieToMovieDetailItem(): MovieDetailItem {
        return MovieDetailItem(
            id = id,
            title = "$name $year",
            description = description,
            slogan = slogan,
            genres = genres.joinToString(", "),
            countries = countries.joinToString(", "),
            url = url,
            actors = actors,
        )
    }
}