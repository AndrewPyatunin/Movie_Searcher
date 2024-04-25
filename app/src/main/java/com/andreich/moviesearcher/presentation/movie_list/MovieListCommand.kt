package com.andreich.moviesearcher.presentation.movie_list

import kotlinx.coroutines.CoroutineScope

sealed interface MovieListCommand {

    class LoadData(val scope: CoroutineScope) : MovieListCommand

    class SearchFilm(val name: String, val scope: CoroutineScope, val requestId: String) :
        MovieListCommand

    class SearchFiltered(val filters: String, val scope: CoroutineScope) : MovieListCommand
}