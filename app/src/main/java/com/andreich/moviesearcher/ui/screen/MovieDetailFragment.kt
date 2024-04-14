package com.andreich.moviesearcher.ui.screen

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.databinding.FragmentMovieDetailBinding
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.presentation.MovieDetailViewModel
import com.andreich.moviesearcher.presentation.ViewModelFactory
import com.andreich.moviesearcher.ui.adapter.ActorAdapter
import com.andreich.moviesearcher.ui.adapter.PosterAdapter
import com.andreich.moviesearcher.ui.adapter.ReviewListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    companion object {

        const val KEY_MOVIE = "movie_detail"

        fun getInstance(movieId: Int): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_MOVIE, movieId)
                }
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MovieDetailViewModel> { viewModelFactory }

    lateinit var movie: Movie
    private val movieId by lazy { arguments?.getInt(KEY_MOVIE) }
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    lateinit var reviewAdapter: ReviewListAdapter
    lateinit var posterAdapter: PosterAdapter

    private val component by lazy { (activity?.application as MovieApp).component }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        viewModel.getMovie(movieId ?: 0)
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

    private fun View.changeVisibility() {
        visibility = if (visibility == GONE) {
            VISIBLE
        } else {
            GONE
        }
    }

    private fun initViews() {
        val recyclerViewActors = binding.movieDetailActorsRecycler
        val recyclerViewReviews = binding.movieDetailReviewsRecycler
        val recyclerViewPosters = binding.movieDetailPostersRecycler
        recyclerViewActors.adapter = ActorAdapter()
        recyclerViewReviews.adapter = ReviewListAdapter()
        recyclerViewPosters.adapter = PosterAdapter()

        reviewAdapter = recyclerViewReviews.adapter as ReviewListAdapter
        posterAdapter = recyclerViewPosters.adapter as PosterAdapter

        recyclerViewActors.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerViewReviews.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerViewReviews.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.movieReviewsTag.setOnClickListener {
            recyclerViewReviews.changeVisibility()
        }
        binding.movieActorsTag.setOnClickListener {
            recyclerViewActors.changeVisibility()
        }
        binding.movieDetailExpandablePostersTag.setOnClickListener {
            recyclerViewPosters.changeVisibility()
        }
        observeViewModel()
        movieId?.let {
            getReviews(it)
            getPosters(it)
        }
    }

    private fun observeViewModel() {
        viewModel.movieLiveData.observe(viewLifecycleOwner) {
            movie = it
            with(binding) {
                movie.let {
                    Glide.with(this@MovieDetailFragment).load(it.url)
                        .into(object : CustomTarget<Drawable>() {
                            override fun onResourceReady(
                                resource: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                movieDetailImage.setImageDrawable(resource)
                                progressImage.visibility = GONE
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                movieDetailImage.setImageDrawable(placeholder)
                                progressImage.visibility = GONE
                            }
                        })
                    movieDetailDescription.text = it.description
                    movieDetailSlogan.text = it.slogan
                    movieDetailGenres.text = it.genres.joinToString(", ")
                    movieDetailCountries.text = it.countries.joinToString(", ")
                    movieDetailTitle.text = it.name + " (${it.year})"
                }
                (movieDetailActorsRecycler.adapter as ActorAdapter).submitList(it.actors)
            }
        }
    }

    private fun getReviews(movieId: Int) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getReviews(movieId).collectLatest {
                    Log.d("MEDIATOR_PAGING", "$it")
                    reviewAdapter.submitData(it)
                }
            }
        }
    }

    private fun getPosters(movieId: Int) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getPosters(movieId).collectLatest {
                    Log.d("MEDIATOR_PAGING", "$it")
                    posterAdapter.submitData(it)
                }
            }
        }
    }
}