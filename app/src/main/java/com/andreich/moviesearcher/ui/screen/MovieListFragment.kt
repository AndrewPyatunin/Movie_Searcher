package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentMovieListBinding
import com.andreich.moviesearcher.presentation.movie_list.MovieListEvent
import com.andreich.moviesearcher.presentation.movie_list.MovieListNews
import com.andreich.moviesearcher.presentation.movie_list.MovieListStore
import com.andreich.moviesearcher.presentation.movie_list.MovieListUiStateMapper
import com.andreich.moviesearcher.ui.Debounce
import com.andreich.moviesearcher.ui.FilterState
import com.andreich.moviesearcher.ui.MovieItem
import com.andreich.moviesearcher.ui.MovieListUiState
import com.andreich.moviesearcher.ui.adapter.HistoryAdapter
import com.andreich.moviesearcher.ui.adapter.MovieListAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tinkoff.kotea.android.lifecycle.collectOnCreate
import ru.tinkoff.kotea.android.storeViaViewModel
import javax.inject.Inject

class MovieListFragment : Fragment() {

    companion object {

        private const val QUERY_FILTER = "query_filter"

        fun getInstance(query: java.io.Serializable?): MovieListFragment {
            return MovieListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(QUERY_FILTER, query)
                }
            }
        }
    }

    private val queryFilter by lazy {
        arguments?.getSerializable(QUERY_FILTER) ?: FilterState.filters
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

    lateinit var adapter: MovieListAdapter
    lateinit var historyAdapter: HistoryAdapter

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
            recyclerHistory.layoutManager = LinearLayoutManager(requireContext())
            recyclerMovies.adapter = MovieListAdapter()
            recyclerHistory.adapter = HistoryAdapter()
            historyAdapter = recyclerHistory.adapter as HistoryAdapter
            adapter = recyclerMovies.adapter as MovieListAdapter
            val filters = queryFilter as Map<String, List<String>>
            if ((filters).isNotEmpty()) {
                clearData()
                FilterState.filters = filters
                store.dispatch(
                    MovieListEvent.MovieListUiEvent.FilterSearchClicked(
                        filters,
                        lifecycleScope
                    )
                )
            } else {
                store.dispatch(MovieListEvent.MovieListUiEvent.LoadData(lifecycleScope))
            }
            historyAdapter.onCLick = {
                cardHistory.visibility = GONE
                searchView.clearFocus()
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
            clickFilter()
            var previousText = ""
            searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
                Log.d("HISTORY_CLICK", previousText)
                if (hasFocus) {
                    store.dispatch(MovieListEvent.MovieListUiEvent.GetHistory)
                } else cardHistory.visibility = GONE
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.d("FRAGMENT", query)
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
                    Log.d("Fragment", "$newText $previousText")
                    if (newText != previousText) {
                        previousText = newText
                        debounce.offer(newText)
                        Log.d("Fragment_list", "clear")
                    }
                    return true
                }

            })
            adapter.onMovieClick = object : MovieListAdapter.OnMovieClickListener {
                override fun onMovieClick(movie: MovieItem) {
                    store.dispatch(MovieListEvent.MovieListUiEvent.MovieItemClicked(movie))
                }
            }
        }
    }

    private fun clickFilter() {
        binding.searchFilterImage.setOnClickListener {
            store.dispatch(MovieListEvent.MovieListUiEvent.FilterMoviesClicked)
        }
    }

    private fun collectState(state: MovieListUiState) {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.d("FRAGMENT", state.movies.toString())
                state.movies?.let {
                    adapter.submitData(it)
                }
            }
        }
        binding.progressLoadMovies.visibility =
            if (state.progressVisibility) VISIBLE else GONE
        binding.recyclerMovies.visibility =
            if (state.progressVisibility) VISIBLE else VISIBLE

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
                Log.d("HISTORY_ADAPTER", news.history.size.toString())
                historyAdapter.submitList(news.history)
            }
        }
    }

    private fun clearData() {
        lifecycleScope.launch {
            adapter.submitData(PagingData.empty())
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