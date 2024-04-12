package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.databinding.FragmentMovieDetailBinding
import com.andreich.moviesearcher.domain.model.Movie
import com.bumptech.glide.Glide

class MovieDetailFragment : Fragment() {

    companion object {

        const val KEY_MOVIE = "movie_detail"

        fun getInstance(movie: Movie): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_MOVIE, movie)
                }
            }
        }
    }

    private val movie by lazy { arguments?.getParcelable<Movie>(KEY_MOVIE) }
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

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
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            movie?.let {
                Glide.with(this@MovieDetailFragment).load(it.url).into(movieDetailImage)
                movieDetailDescription.text = it.description
                movieDetailSlogan.text = it.slogan
                movieDetailGenres.text = it.genres.joinToString(", ")
                movieDetailCountries.text = it.countries.joinToString(", ")
                movieDetailTitle.text = it.name + " (${it.year})"
            }
        }
    }
}