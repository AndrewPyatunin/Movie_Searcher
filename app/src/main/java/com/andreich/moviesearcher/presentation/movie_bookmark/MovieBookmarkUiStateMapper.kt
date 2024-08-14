package com.andreich.moviesearcher.presentation.movie_bookmark

import android.graphics.Color
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.ui.MovieItem
import com.andreich.moviesearcher.ui.state.MovieBookmarkUiState
import ru.tinkoff.kotea.android.ui.ResourcesProvider
import ru.tinkoff.kotea.android.ui.UiStateMapper

class MovieBookmarkUiStateMapper : UiStateMapper<MovieBookmarkState, MovieBookmarkUiState> {

    override fun ResourcesProvider.map(state: MovieBookmarkState): MovieBookmarkUiState {
        val movies = state.movies.map {
            val color = when {
                it.rating >= 7.0 ->  Color.GREEN
                it.rating > 0.0 && it.rating < 5.0 -> Color.RED
                else -> Color.GRAY
            }
            mapMovieToMovieItem(it, color)
        }
        return MovieBookmarkUiState(
            state.isLoading,
            movies
        )
    }

    private fun mapMovieToMovieItem(movie: Movie, color: Int): MovieItem {
        return MovieItem(
            id = movie.id,
            name = if (movie.isSeries) String.format("%s (сериал)", movie.name) else movie.name,
            alternativeName = movie.alternativeName,
            type = movie.type,
            year = movie.year.toString(),
            ratingColor = color,
            rating = movie.rating.let { "%.1f".format(it) },
            genres = movie.genres.joinToString(", "),
            countries = movie.countries.joinToString(", "),
            previewUrl = movie.previewUrl,
            filmLength = if (movie.isSeries) movie.seriesLength.toString() else movie.movieLength.toString(),

        )
    }
}