package com.andreich.moviesearcher.di

import android.app.Application
import com.andreich.moviesearcher.ui.screen.LoginFragment
import com.andreich.moviesearcher.ui.screen.MainActivity
import com.andreich.moviesearcher.ui.screen.MovieDetailFragment
import com.andreich.moviesearcher.ui.screen.MovieListFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Component.Factory

@AppScope
@Component(modules = [DataModule::class, DomainModule::class, DatabaseModule::class, EntityMapperModule::class, ApiModule::class, DtoModule::class])
interface AppComponent {

    @Factory
    interface ComponentFactory {

        fun create(@BindsInstance context: Application): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: MovieListFragment)

    fun inject(fragment: MovieDetailFragment)
}