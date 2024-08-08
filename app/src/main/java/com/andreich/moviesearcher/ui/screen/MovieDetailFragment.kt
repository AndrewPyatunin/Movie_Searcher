package com.andreich.moviesearcher.ui.screen

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentMovieDetailBinding
import com.andreich.moviesearcher.domain.model.Actor
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailEvent
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailNews
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailStore
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailUiStateMapper
import com.andreich.moviesearcher.ui.MovieDetailItem
import com.andreich.moviesearcher.ui.adapter.ActorAdapter
import com.andreich.moviesearcher.ui.adapter.ActorPagingAdapter
import com.andreich.moviesearcher.ui.adapter.PosterAdapter
import com.andreich.moviesearcher.ui.adapter.ReviewListAdapter
import com.andreich.moviesearcher.ui.state.MovieDetailUiState
import com.andreich.moviesearcher.ui.view.CustomTextViewWithImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.tinkoff.kotea.android.lifecycle.collectOnCreate
import ru.tinkoff.kotea.android.storeViaViewModel
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

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainActivity", throwable.message, throwable)
    }

    @Inject
    lateinit var movieDetailStore: MovieDetailStore

    @Inject
    lateinit var mapper: MovieDetailUiStateMapper

    private val store by storeViaViewModel(Dispatchers.Default + coroutineExceptionHandler) { movieDetailStore }

    private val movieId by lazy { arguments?.getInt(KEY_MOVIE) }
    
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    private val reviewAdapter: ReviewListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ReviewListAdapter()
    }
    private val posterAdapter: PosterAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PosterAdapter()
    }
    private val actorAdapter: ActorPagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
         ActorPagingAdapter()
    }

    private val personAdapter: ActorAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ActorAdapter()
    }

    private val component by lazy { (activity?.application as MovieApp).component }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        store.collectOnCreate(
            fragment = this,
            uiStateMapper = mapper,
            stateCollector = ::collectState,
            newsCollector = ::handleNews,
        )
        Log.d("ACTORS_TO_MOVIE_DETAIL", movieId.toString())
        store.dispatch(MovieDetailEvent.MovieDetailUiEvent.LoadMovie(lifecycleScope + Dispatchers.Default, movieId ?: 0))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun collectState(state: MovieDetailUiState) {
        lifecycleScope.launch(Dispatchers.IO) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                reviewAdapter.submitData(state.reviews)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.d("PosterAdapterFragment", state.posters.toString())
                posterAdapter.submitData(state.posters)
            }
        }
        state.movieDetailItem?.let {
            initScreen(it)
        }

    }

    private fun handleNews(news: MovieDetailNews) {
        when (news) {
            is MovieDetailNews.ShowError -> {
                Toast.makeText(requireContext(), news.message, Toast.LENGTH_SHORT).show()
            }
            is MovieDetailNews.NavigateTo -> {
                navigateTo(news.fragment)
            }
        }
    }

    private fun View.changeVisibility() {
        visibility = if (visibility == GONE) {
            VISIBLE
        } else {
            GONE
        }
    }

    private fun CustomTextViewWithImage.changeImageRes() {
        get(1).changeVisibility()
        get(2).changeVisibility()
    }

    private fun initViews() {
        val recyclerViewActors = binding.movieDetailActorsRecycler
        val recyclerViewReviews = binding.movieDetailReviewsRecycler
        val recyclerViewPosters = binding.movieDetailPostersRecycler

        recyclerViewActors.adapter = personAdapter
        recyclerViewReviews.adapter = reviewAdapter
        recyclerViewPosters.adapter = posterAdapter

        actorAdapter.onActorClick = object : ActorPagingAdapter.OnActorPagingClickListener {
            override fun onActorClick(person: Person) {
                store.dispatch(MovieDetailEvent.MovieDetailUiEvent.NavigateTo(ActorDetailFragment.getInstance(person.id ?: 0)))
            }
        }

        personAdapter.onClick = object : ActorAdapter.OnActorClickListener {
            override fun onPersonClick(person: Person) {
                store.dispatch(MovieDetailEvent.MovieDetailUiEvent.NavigateTo(ActorDetailFragment.getInstance(person.id ?: 0)))
            }
        }

        recyclerViewActors.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerViewReviews.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerViewPosters.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        binding.movieReviewsTag.setOnClickListener {
            recyclerViewReviews.changeVisibility()
            binding.movieReviewsTag.changeImageRes()
        }
        binding.movieActorsTag.setOnClickListener {
            recyclerViewActors.changeVisibility()
            binding.movieActorsTag.changeImageRes()
        }
        binding.moviePostersTag.setOnClickListener {
            recyclerViewPosters.changeVisibility()
            binding.moviePostersTag.changeImageRes()
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun initScreen(movie: MovieDetailItem) {
        with(binding) {
            movie.let {
                Glide.with(this@MovieDetailFragment).load(it.url)
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            movieDetailImage.setImageDrawable(resource)
                            shimmerDetail.visibility = GONE
                            movieDetailImage.visibility = VISIBLE
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            movieDetailImage.setImageDrawable(placeholder)
                            shimmerDetail.visibility = GONE
                            movieDetailImage.visibility = VISIBLE
                        }
                    })
                movieDetailDescription.text = it.description
                movieDetailSlogan.text = it.slogan
                movieDetailGenres.text = it.genres
                movieDetailCountries.text = it.countries
                movieDetailTitle.text = it.title
                personAdapter.submitList(it.actors)
            }
        }
    }
}