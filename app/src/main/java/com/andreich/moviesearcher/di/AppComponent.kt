package com.andreich.moviesearcher.di

import android.app.Application
import com.andreich.moviesearcher.MainActivity
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
}