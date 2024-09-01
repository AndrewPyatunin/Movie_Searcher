package com.andreich.moviesearcher.presentation.movie_bookmark

sealed interface MovieBookmarkCommand {

    object LoadMovies : MovieBookmarkCommand

    class RemoveFromBookmark(val movieId: Int, val movieTitle: String) : MovieBookmarkCommand
}