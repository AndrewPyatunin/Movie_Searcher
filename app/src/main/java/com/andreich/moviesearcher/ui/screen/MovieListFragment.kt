package com.andreich.moviesearcher.ui.screen

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.graphics.alpha
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentMovieListBinding
import com.andreich.moviesearcher.domain.QUERY_AGE_RATING
import com.andreich.moviesearcher.domain.QUERY_COUNTRY
import com.andreich.moviesearcher.domain.QUERY_YEAR
import com.andreich.moviesearcher.presentation.movie_filter.MovieFilterState
import com.andreich.moviesearcher.presentation.movie_list.MovieListEvent
import com.andreich.moviesearcher.presentation.movie_list.MovieListNews
import com.andreich.moviesearcher.presentation.movie_list.MovieListStore
import com.andreich.moviesearcher.presentation.movie_list.MovieListUiStateMapper
import com.andreich.moviesearcher.ui.Debounce
import com.andreich.moviesearcher.ui.FilterState
import com.andreich.moviesearcher.ui.MovieItem
import com.andreich.moviesearcher.ui.adapter.HistoryAdapter
import com.andreich.moviesearcher.ui.adapter.MovieListAdapter
import com.andreich.moviesearcher.ui.state.MovieListUiState
import com.andreich.moviesearcher.ui.view.CustomTextViewWithImage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.tinkoff.kotea.android.lifecycle.collectOnCreate
import ru.tinkoff.kotea.android.storeViaViewModel
import javax.inject.Inject


class MovieListFragment : Fragment() {

    companion object {

        private const val QUERY_FILTER = "query_filter"

        fun getInstance(query: MovieFilterState?): MovieListFragment {
            return MovieListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(QUERY_FILTER, query)
                }
            }
        }
    }

    private val queryFilter by lazy {
        arguments?.getParcelable(QUERY_FILTER) ?: FilterState.filters
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainActivity", throwable.message, throwable)
    }

    @Inject
    lateinit var movieListStore: MovieListStore

    private val store by storeViaViewModel(Dispatchers.Default + coroutineExceptionHandler) {
        movieListStore
    }

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    private val component by lazy { (activity?.application as MovieApp).component }

    private val debounce by lazy {
        Debounce(1000, lifecycleScope) {
            Log.d("LIST_FRAGMENT_SEARCH", it)
            clearData()
            store.dispatch(
                MovieListEvent.MovieListUiEvent.SearchClicked(
                    it,
                    lifecycleScope,
                    it
                )
            )
        }
    }

    private val movieListAdapter: MovieListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieListAdapter()
    }

    private val historyAdapter: HistoryAdapter by lazy(LazyThreadSafetyMode.NONE) {
        HistoryAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        store.collectOnCreate(
            this,
            uiStateMapper = MovieListUiStateMapper(),
            stateCollector = ::collectState,
            newsCollector = ::handleNews,
        )
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
        with(binding) {
            recyclerMovies.layoutManager = LinearLayoutManager(requireContext())
            recyclerHistory.layoutManager = LinearLayoutManager(context)
            recyclerMovies.adapter = movieListAdapter
            recyclerHistory.adapter = historyAdapter
            val filters = queryFilter?.filters ?: emptyMap()
            if ((filters).isNotEmpty()) {
                Log.d("LIST_FRAGMENT", "${filters.size}")
                clearData()
                store.dispatch(
                    MovieListEvent.MovieListUiEvent.FilterSearchClicked(
                        filters,
                        lifecycleScope
                    )
                )
            } else {
                Log.d("LIST_FRAGMENT", "0")
                store.dispatch(MovieListEvent.MovieListUiEvent.LoadData(lifecycleScope + Dispatchers.Default))
            }
            clickFavourites()
            listenAdapter()
            onHistoryAdapterClick()
            clickSort()
            clickFilter()
            menuItemClick()
            onSearchQuery()
            searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
                if (hasFocus) store.dispatch(MovieListEvent.MovieListUiEvent.GetHistory)
            }
            movieListAdapter.onMovieClick = object : MovieListAdapter.OnMovieClickListener {
                override fun onMovieClick(movie: MovieItem) {
                    store.dispatch(MovieListEvent.MovieListUiEvent.MovieItemClicked(movie))
                }
            }
        }
    }

    private fun onHistoryAdapterClick() {
        historyAdapter.onCLick = {
            binding.cardHistory.visibility = GONE
            binding.searchView.clearFocus()
            clearData()
            store.dispatch(
                MovieListEvent.MovieListUiEvent.SearchClicked(
                    it.movieTitle,
                    lifecycleScope,
                    it.movieTitle
                )
            )
            binding.searchView.setQuery(it.movieTitle, true)
        }
    }

    private fun onSearchQuery() {
        var previousText = ""
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("LIST_FRAGMENT_SEARCH", query.length.toString())
                clearData()
                store.dispatch(
                    MovieListEvent.MovieListUiEvent.SearchClicked(
                        query,
                        lifecycleScope,
                        query
                    )
                )
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != previousText) {
                    previousText = newText
                    debounce.offer(newText)
                }
                return true
            }
        })
    }

    private fun View.changeVisibility() {
        visibility = if (visibility == GONE) {
            VISIBLE
        } else GONE
    }

    private fun CustomTextViewWithImage.changeImageRes() {
        get(1).changeVisibility()
        get(2).changeVisibility()
    }

    private fun clickSort() {
        with(binding) {
            sortCountry.onSortClick()
            sortDate.onSortClick()
            sortAgeRating.onSortClick()
        }
    }

    private fun CustomTextViewWithImage.onSortClick() {
        with(binding) {
            val sortList = mutableListOf(sortCountry, sortAgeRating, sortDate)
            setOnClickListener {
                sortList.remove(it)
                (it as CustomTextViewWithImage).sortClick(it[1].visibility == GONE)
                it.changeImageRes()
                it.changeToInitialBackground((background as ColorDrawable).color.alpha)
            }
        }
    }

    private fun clickFilter() {
        binding.searchFilterImage.setOnClickListener {
            store.dispatch(MovieListEvent.MovieListUiEvent.FilterMoviesClicked(queryFilter))
        }
    }

    private fun clickFavourites() {
        binding.imageOpenFavourites.setOnClickListener {
            store.dispatch(MovieListEvent.MovieListUiEvent.FavouritesClicked)
        }
    }

    private fun menuItemClick() {
        movieListAdapter.onMenuClick = object : MovieListAdapter.OnMenuItemClickListener {
            override fun onAddBookmarkClick(movie: MovieItem) {
                store.dispatch(MovieListEvent.MovieListUiEvent.AddToBookmarkClicked(movie.id, movie.name))
            }

            override fun onRemoveBookmarkClick(movie: MovieItem) {
                store.dispatch(MovieListEvent.MovieListUiEvent.RemoveFromBookmarkClicked(movie.id, movie.name))
            }

        }
    }

    private fun CustomTextViewWithImage.sortClick(
        isDown: Boolean,
        sortQuery: Map<String, Int> = emptyMap()
    ) {
        clearData()
        val sortType = if (isDown) -1 else 1
        when (this.id) {
            R.id.sort_age_rating -> {
                store.dispatch(
                    MovieListEvent.MovieListUiEvent.SortedSearchCLicked(
                        mapOf(
                            Pair(QUERY_AGE_RATING, sortType)
                        ), "sort_ageRating_$sortType",
                        lifecycleScope
                    )
                )
            }
            R.id.sort_country -> {
                store.dispatch(
                    MovieListEvent.MovieListUiEvent.SortedSearchCLicked(
                        mapOf(
                            Pair(QUERY_COUNTRY, sortType)
                        ), "sort_country_$sortType",
                        lifecycleScope
                    )
                )
            }
            R.id.sort_date -> {
                store.dispatch(
                    MovieListEvent.MovieListUiEvent.SortedSearchCLicked(
                        mapOf(
                            Pair(QUERY_YEAR, sortType)
                        ), "sort_date_$sortType", lifecycleScope
                    )
                )
            }
            else -> {

            }
        }
    }

    private fun collectState(state: MovieListUiState) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                state.movies?.let {
                    movieListAdapter.submitData(it)
                }
            }
        }
        Log.d("PAGING_PROGRESS", "${state.progressVisibility}")
        binding.progressLoadMovies.visibility =
            if (state.progressVisibility) VISIBLE else INVISIBLE
    }

    private fun handleNews(news: MovieListNews) {
        when (news) {
            is MovieListNews.NavigateTo -> {
                navigateTo(news.fragment)
            }
            is MovieListNews.ShowErrorToast -> {
                Toast.makeText(requireContext(), news.message, Toast.LENGTH_SHORT).show()
            }
            is MovieListNews.ShowHistory -> {
                binding.cardHistory.visibility = VISIBLE
                historyAdapter.submitList(news.history)
            }
            is MovieListNews.ShowBookmarkToast -> {
                Toast.makeText(
                    requireContext(),
                    "Фильм ${news.movieTitle} ${if (news.isAdded) " добавлен в избранное" else " удален из избранного"}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun listenAdapter() {
        movieListAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading) {
                store.dispatch(MovieListEvent.MovieListUiEvent.PaginationLoad)
            } else {
                store.dispatch(MovieListEvent.MovieListUiEvent.PaginationStopLoad)
            }
        }
    }

    private fun clearData() {
        lifecycleScope.launch {
            movieListAdapter.submitData(PagingData.empty())
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}