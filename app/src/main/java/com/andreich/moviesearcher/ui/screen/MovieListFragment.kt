package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding ?: throw RuntimeException("Binding is null!")

    private val component by lazy { (activity?.application as MovieApp).component }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}