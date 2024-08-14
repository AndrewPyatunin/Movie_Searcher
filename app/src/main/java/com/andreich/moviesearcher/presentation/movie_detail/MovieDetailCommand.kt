package com.andreich.moviesearcher.presentation.movie_detail

import com.andreich.moviesearcher.domain.model.Movie
import kotlinx.coroutines.CoroutineScope

sealed interface MovieDetailCommand {

    class LoadMovie(val movieId: Int, val scope: CoroutineScope) : MovieDetailCommand

    class LoadReviews(val movieId: Int, val scope: CoroutineScope) : MovieDetailCommand

    class LoadPersons(val movieId: Int, val scope: CoroutineScope) : MovieDetailCommand

    class LoadPosters(val movieId: Int, val scope: CoroutineScope) : MovieDetailCommand

    class AddToBookmark(val movieId: Int, val isBookmark: Boolean) : MovieDetailCommand
}