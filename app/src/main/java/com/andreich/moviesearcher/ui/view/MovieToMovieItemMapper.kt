package com.andreich.moviesearcher.ui.view

import android.content.Context
import androidx.core.content.ContextCompat
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.ui.MovieItem

object MovieToMovieItemMapper {

    fun mapMovieToMovieItem(movie: Movie, context: Context): MovieItem {
        return with(movie) {
            val color: Int = when {
                rating >= 7.0 -> {
                    ContextCompat.getColor(context, R.color.green)
                }
                rating in 5.0 .. 6.99 || rating == 0.0 -> {
                    ContextCompat.getColor(context, R.color.gray)
                }
                rating < 5.0 -> {
                    ContextCompat.getColor(context, R.color.red)
                }
                else -> {
                    ContextCompat.getColor(context, R.color.green)
                }
            }
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