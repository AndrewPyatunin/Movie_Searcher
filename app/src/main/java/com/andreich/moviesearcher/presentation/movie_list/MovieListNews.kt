package com.andreich.moviesearcher.presentation.movie_list

import androidx.fragment.app.Fragment

sealed interface MovieListNews {

    class NavigateTo(val fragment: Fragment) : MovieListNews

    class ShowErrorToast(val message: String) : MovieListNews
}