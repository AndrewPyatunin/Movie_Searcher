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
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            Log.d("Fragment", "backstack")
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }
}