package com.andreich.moviesearcher.ui.screen

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.alpha
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
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailEvent
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailNews
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailStore
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailUiStateMapper
import com.andreich.moviesearcher.ui.MovieDetailItem
import com.andreich.moviesearcher.ui.adapter.*
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
        Log.e("MovieDetailFragment", throwable.message, throwable)
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
    private val posterAdapter: PosterListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PosterListAdapter()
    }
    private val actorAdapter: ActorPagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ActorPagingAdapter()
    }

    private val personAdapter: ActorAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ActorAdapter()
    }

    private val seasonAdapter: SeasonAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SeasonAdapter()
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
        store.dispatch(
            MovieDetailEvent.MovieDetailUiEvent.LoadMovie(
                lifecycleScope + Dispatchers.Default,
                movieId ?: 0
            )
        )
        store.dispatch(MovieDetailEvent.MovieDetailUiEvent.LoadPosters(
            movieId ?: 0,
            lifecycleScope + Dispatchers.IO
        ))
        store.dispatch(MovieDetailEvent.MovieDetailUiEvent.LoadActors(
            movieId ?: 0,
            lifecycleScope + Dispatchers.IO
        ))
        store.dispatch(MovieDetailEvent.MovieDetailUiEvent.LoadReviews(
            movieId ?: 0,
            lifecycleScope + Dispatchers.IO
        ))
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun collectState(state: MovieDetailUiState) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                reviewAdapter.submitData(state.reviews)
            }
        }
        lifecycleScope.launch {
            with(binding) {
                if (reviewAdapter.snapshot().isNullOrEmpty()) {
                    Log.d("REVIEW_ADAPTER_EMPTY", "empty")
                    movieReviewsTag.visibility = GONE
                    movieDetailReviewsRecycler.visibility = GONE
                } else {
                    Log.d("REVIEW_ADAPTER_NOT_EMPTY", "not_empty")
                    movieReviewsTag.visibility = VISIBLE
                    movieDetailReviewsRecycler.visibility = VISIBLE
                }
            }
        }
        lifecycleScope.launch {
            with(binding) {
                with(state) {
                    if (seasons.isEmpty() || movieDetailItem?.isSeries == false) {
                        movieSeasonRecycler.visibility = GONE
                        movieSeasonTag.visibility = GONE
                    } else {
                        movieSeasonRecycler.visibility = VISIBLE
                        movieSeasonTag.visibility = VISIBLE
                    }
                    if (posters.isEmpty()) {
                        moviePostersTag.visibility = GONE
                        movieDetailPostersRecycler.visibility = GONE
                    } else {
                        moviePostersTag.visibility = VISIBLE
                        movieDetailPostersRecycler.visibility = VISIBLE
                    }
                }
            }

            state.seasons.let {
                seasonAdapter.submitList(it)
            }
        }
        lifecycleScope.launch {
            posterAdapter.submitList(state.posters)
        }
        state.movieDetailItem?.let {
            initScreen(it)
        }
        lifecycleScope.launch {
            state.bookmarkType.let {
                with(binding) {
                    when (it) {
                        true -> {
                            bookmarkAdd.setImageResource(R.drawable.bookmark_remove)
                        }
                        false -> {
                            bookmarkAdd.setImageResource(R.drawable.bookmark_add)
                        }
                    }
                }
            }
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
            is MovieDetailNews.ShowToast -> {
                Toast.makeText(context, news.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun View.changeVisibility() {
        visibility = if (visibility == GONE) VISIBLE else GONE
    }

    private fun CustomTextViewWithImage.changeImageRes() {
        get(1).changeVisibility()
        get(2).changeVisibility()
    }

    private fun initViews() {
        val recyclerViewActors = binding.movieDetailActorsRecycler
        val recyclerViewReviews = binding.movieDetailReviewsRecycler
        val recyclerViewPosters = binding.movieDetailPostersRecycler
        val recyclerViewSeasons = binding.movieSeasonRecycler

        recyclerViewActors.adapter = personAdapter
        recyclerViewReviews.adapter = reviewAdapter
        recyclerViewPosters.adapter = posterAdapter
        recyclerViewSeasons.adapter = seasonAdapter

        actorAdapter.onActorClick = object : ActorPagingAdapter.OnActorPagingClickListener {
            override fun onActorClick(person: Person) {
                store.dispatch(
                    MovieDetailEvent.MovieDetailUiEvent.NavigateTo(
                        ActorDetailFragment.getInstance(
                            person.id ?: 0
                        )
                    )
                )
            }
        }

        personAdapter.onClick = object : ActorAdapter.OnActorClickListener {
            override fun onPersonClick(person: Person) {
                store.dispatch(
                    MovieDetailEvent.MovieDetailUiEvent.NavigateTo(
                        ActorDetailFragment.getInstance(
                            person.id ?: 0
                        )
                    )
                )
            }
        }
        recyclerViewPosters.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerViewPosters.apply {
            set3DItem(true)
            setIsScrollingEnabled(true)
            setAlpha(true)
        }

        with(binding) {
            bookmarkAdd.setOnClickListener {
                Log.d("BOOKMARK", "clicked")
                bookmarkClick(true)
            }
            bookmarksOpen.setOnClickListener {
                store.dispatch(MovieDetailEvent.MovieDetailUiEvent.NavigateTo(MovieBookmarkFragment.getInstance()))
            }
            movieReviewsTag.setOnClickListener {
                movieReviewsTag.changeColorToInitial()
                recyclerViewReviews.changeVisibility()
                movieReviewsTag.changeImageRes()
            }
            movieActorsTag.setOnClickListener {
                movieActorsTag.changeColorToInitial()
                recyclerViewActors.changeVisibility()
                movieActorsTag.changeImageRes()
            }
            moviePostersTag.setOnClickListener {
                moviePostersTag.changeColorToInitial()
                recyclerViewPosters.changeVisibility()
                moviePostersTag.changeImageRes()
            }
        }

    }

    private fun CustomTextViewWithImage.changeColorToInitial() {
        changeToInitialBackground((background as ColorDrawable).color.alpha)
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun bookmarkClick(isBookmark: Boolean) {
        store.dispatch(MovieDetailEvent.MovieDetailUiEvent.AddToBookmark(movieId ?: 0, isBookmark))
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
                if (it.actors.isEmpty()) {
                    movieDetailActorsRecycler.visibility = GONE
                    movieActorsTag.visibility = GONE
                } else {
                    movieDetailActorsRecycler.visibility = VISIBLE
                    movieActorsTag.visibility = VISIBLE
                }
                if (it.isSeries) {
                    store.dispatch(
                        MovieDetailEvent.MovieDetailUiEvent.LoadSeasons(
                            it.id
                        )
                    )
                }
            }
        }
    }
}