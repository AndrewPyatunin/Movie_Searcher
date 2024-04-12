package com.andreich.moviesearcher.ui.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = MovieListFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}