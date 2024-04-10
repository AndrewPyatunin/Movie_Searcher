package com.andreich.moviesearcher

import android.app.Application
import com.andreich.moviesearcher.di.DaggerAppComponent

class MovieApp: Application() {
    val component by lazy { DaggerAppComponent.factory().create(this) }

}