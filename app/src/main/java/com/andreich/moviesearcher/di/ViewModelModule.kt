package com.andreich.moviesearcher.di

import androidx.lifecycle.ViewModel
import com.andreich.moviesearcher.ui.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @Binds
    @ViewModelKey(MovieListViewModel::class)
    fun bindMovieListViewModel(impl: MovieListViewModel): ViewModel
}