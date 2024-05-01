package com.andreich.moviesearcher.presentation.movie_filter

import androidx.fragment.app.Fragment

sealed interface MovieFilterNews {

    class NavigateTo(val fragment: Fragment) : MovieFilterNews

    class ShowError(val message: String) : MovieFilterNews
}