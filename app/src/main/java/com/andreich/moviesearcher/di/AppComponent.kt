package com.andreich.moviesearcher.di

import android.content.Context
import com.andreich.moviesearcher.presentation.MovieListStore
import com.andreich.moviesearcher.ui.screen.LoginFragment
import com.andreich.moviesearcher.ui.screen.MainActivity
import com.andreich.moviesearcher.ui.screen.MovieDetailFragment
import com.andreich.moviesearcher.ui.screen.MovieListFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, DomainModule::class, DatabaseModule::class,
    EntityMapperModule::class, ApiModule::class, DtoModule::class,
    ViewModelModule::class, StoreModule::class])
interface AppComponent {

    @Component.Factory
    interface ComponentFactory {

        fun create(@BindsInstance context: Context, @BindsInstance apiKey: String): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: MovieListFragment)

    fun inject(fragment: MovieDetailFragment)
}