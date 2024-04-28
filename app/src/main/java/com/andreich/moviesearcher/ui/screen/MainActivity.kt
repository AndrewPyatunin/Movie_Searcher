package com.andreich.moviesearcher.ui.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.window.OnBackInvokedDispatcher
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = MovieListFragment.getInstance(null)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is MovieListFragment) {
            finish()
            Log.d("Fragment", "backstack")
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                Log.d("Fragment", "backstack")
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressedDispatcher.onBackPressed()
            }
        }
    }

}