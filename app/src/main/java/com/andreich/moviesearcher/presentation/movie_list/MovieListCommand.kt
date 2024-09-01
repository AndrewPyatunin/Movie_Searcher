package com.andreich.moviesearcher.presentation.movie_list

import kotlinx.coroutines.CoroutineScope

sealed interface MovieListCommand {

    class LoadData(val scope: CoroutineScope) : MovieListCommand

    class SearchSorted(val sortFilters: Map<String, Int>, val sortId: String, val scope: CoroutineScope) : MovieListCommand

    class SearchFilm(val name: String, val scope: CoroutineScope, val requestId: String) :
        MovieListCommand

    class SearchFiltered(val filters: Map<String, List<String>>, val scope: CoroutineScope) : MovieListCommand

    class AddToBookmark(val movieId: Int, val movieTitle: String) : MovieListCommand

    class RemoveFromBookmark(val movieId: Int, val movieTitle: String) : MovieListCommand

    object GetHistory : MovieListCommand
}