package com.andreich.moviesearcher.di

import com.andreich.moviesearcher.presentation.HtmlStringFormatter
import com.andreich.moviesearcher.presentation.HtmlStringFormatterImpl
import dagger.Module
import dagger.Provides

@Module
class HtmlFormatterModule {

    @Provides
    fun provideHtmlFormatter() : HtmlStringFormatter {
        return HtmlStringFormatterImpl()
    }
}