package com.andreich.moviesearcher.presentation.movie_detail

import androidx.fragment.app.Fragment

sealed interface MovieDetailNews {

    class ShowError(val message: String) : MovieDetailNews

    class NavigateTo(val fragment: Fragment) : MovieDetailNews
}