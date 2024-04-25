package com.andreich.moviesearcher.presentation.movie_detail

import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.ui.MovieDetailItem
import com.andreich.moviesearcher.ui.state.MovieDetailUiState
import ru.tinkoff.kotea.android.ui.ResourcesProvider
import ru.tinkoff.kotea.android.ui.UiStateMapper
import kotlin.math.max
import kotlin.math.min

class MovieDetailUiStateMapper : UiStateMapper<MovieDetailState, MovieDetailUiState> {

    override fun ResourcesProvider.map(state: MovieDetailState): MovieDetailUiState {
        return MovieDetailUiState(
            imageProgressVisibility = state.isLoading,
            movieDetailItem = state.movie?.mapMovieToMovieDetailItem(),
            actors = state.persons,
            reviews = state.reviews,
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