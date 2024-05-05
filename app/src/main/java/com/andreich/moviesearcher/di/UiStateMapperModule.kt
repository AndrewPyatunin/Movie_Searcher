package com.andreich.moviesearcher.di

import com.andreich.moviesearcher.presentation.HtmlStringFormatter
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailUiStateMapper
import dagger.Module
import dagger.Provides

@Module
class UiStateMapperModule {

    @Provides
    fun provideMovieDetailUiStateMapper(
        htmlStringFormatter: HtmlStringFormatter
    ): MovieDetailUiStateMapper {
        return MovieDetailUiStateMapper(htmlStringFormatter)
    }
}