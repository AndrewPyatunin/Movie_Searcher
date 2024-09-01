package com.andreich.moviesearcher.presentation.movie_bookmark

import androidx.fragment.app.Fragment

sealed interface MovieBookmarkNews {

    class ShowError(val message: String) : MovieBookmarkNews

    class NavigateTo(val fragment: Fragment) : MovieBookmarkNews

    class ShowResult(val message: String) : MovieBookmarkNews
}