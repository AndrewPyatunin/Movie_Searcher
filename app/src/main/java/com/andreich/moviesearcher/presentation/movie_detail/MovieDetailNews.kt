package com.andreich.moviesearcher.presentation.movie_detail

sealed interface MovieDetailNews {

    class ShowError(val message: String) : MovieDetailNews
}