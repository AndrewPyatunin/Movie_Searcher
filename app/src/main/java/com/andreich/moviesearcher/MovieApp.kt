package com.andreich.moviesearcher

import android.app.Application
import com.andreich.moviesearcher.di.DaggerAppComponent

class MovieApp: Application() {
    val component by lazy { DaggerAppComponent.factory().create(this, "") }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        lateinit var app: MovieApp

        fun getApplication(): MovieApp {
            return app
        }
    }
}