package com.andreich.moviesearcher.presentation.movie_list

import androidx.fragment.app.Fragment
import com.andreich.moviesearcher.domain.model.MovieSearchHistory

sealed interface MovieListNews {

    class NavigateTo(val fragment: Fragment) : MovieListNews

    class ShowErrorToast(val message: String) : MovieListNews

    class ShowHistory(val history: List<MovieSearchHistory>) : MovieListNews
}