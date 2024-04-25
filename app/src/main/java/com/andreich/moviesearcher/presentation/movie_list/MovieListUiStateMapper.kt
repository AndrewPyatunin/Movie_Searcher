package com.andreich.moviesearcher.presentation.movie_list

import androidx.paging.map
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.ui.MovieItem
import com.andreich.moviesearcher.ui.MovieListUiState
import ru.tinkoff.kotea.android.ui.ResourcesProvider
import ru.tinkoff.kotea.android.ui.UiStateMapper

class MovieListUiStateMapper : UiStateMapper<MovieListState, MovieListUiState> {

    override fun ResourcesProvider.map(state: MovieListState): MovieListUiState {
        val movies = state.movies.map {
            val color: Int = when {
                it.rating >= 7.0 -> {
                    getColor(R.color.green)
                }
                it.rating in 5.0..6.99 || it.rating == 0.0 -> {
                    getColor(R.color.gray)
                }
                it.rating < 5.0 -> {
                    getColor(R.color.red)
                }
                else -> {
                    getColor(R.color.green)
                }
            }
            mapMovieToMovieItem(it, color)
        }
        return MovieListUiState(
            movies = movies,
            progressVisibility = state.isLoading,
            listVisibility = !state.isLoading
        )
    }

    private fun mapMovieToMovieItem(movie: Movie, color: Int): MovieItem {

        return with(movie) {
            MovieItem(
                id = id,
                name = if (isSeries) String.format("%s (сериал)", name) else name,
                alternativeName = alternativeName,
                type = type,
                year = year.toString(),
                rating = rating.let { "%.1f".format(it) },
                ratingColor = color,
                genres = genres.joinToString(", "),
                countries = countries.joinToString(", "),
                previewUrl = previewUrl,
                filmLength = if (isSeries) seriesLength.toString() else movieLength.toString(),
            )
        }
    }
}