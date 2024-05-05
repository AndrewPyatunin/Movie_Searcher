package com.andreich.moviesearcher.ui.screen

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.window.OnBackInvokedDispatcher
import androidx.fragment.app.FragmentManager
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = MovieListFragment.getInstance(null)
        supportFragmentManager.addOnBackStackChangedListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getLocalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    hideKeyboard(v)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun hideKeyboard(editText: EditText) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showActionBar(isShow: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(isShow)
        supportActionBar?.setDisplayShowHomeEnabled(isShow)
    }

    override fun onBackStackChanged() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is MovieListFragment) {
            showActionBar(false)
            Log.d("Fragment", "backstack")
        } else {
            showActionBar(true)
        }
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