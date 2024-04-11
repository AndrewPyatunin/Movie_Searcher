package com.andreich.moviesearcher.ui.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R

class MainActivity : AppCompatActivity() {

    private val component by lazy {
        (application as MovieApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
    }
}