package com.andreich.moviesearcher.di

import android.content.Context
import com.andreich.moviesearcher.ui.screen.*
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [DataModule::class, DomainModule::class, DatabaseModule::class,
        EntityMapperModule::class, ApiModule::class, DtoModule::class, StoreModule::class,
        HtmlFormatterModule::class, UiStateMapperModule::class]
)
interface AppComponent {

    @Component.Factory
    interface ComponentFactory {

        fun create(@BindsInstance context: Context, @BindsInstance apiKey: String): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: MovieFilterFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: MovieListFragment)

    fun inject(fragment: MovieDetailFragment)
}