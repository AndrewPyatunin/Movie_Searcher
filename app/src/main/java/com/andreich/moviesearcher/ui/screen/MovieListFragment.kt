package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentMovieListBinding
import com.andreich.moviesearcher.presentation.*
import com.andreich.moviesearcher.ui.Debounce
import com.andreich.moviesearcher.ui.MovieItem
import com.andreich.moviesearcher.ui.MovieListUiState
import com.andreich.moviesearcher.ui.MovieListUiStateMapper
import com.andreich.moviesearcher.ui.adapter.MovieListAdapter
import com.andreich.moviesearcher.ui.view.MovieToMovieItemMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.tinkoff.kotea.android.lifecycle.collectOnCreate
import ru.tinkoff.kotea.android.storeViaViewModel
import javax.inject.Inject

class MovieListFragment : Fragment() {

    companion object {

        fun getInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainActivity", throwable.message, throwable)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MovieListViewModel> { viewModelFactory }

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
//            searchFilm(it)
            store.dispatch(MovieListEvent.MovieListUiEvent.SearchClicked(it, lifecycleScope, "?query=$it"))
        }
    }

    lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        store.collectOnCreate(
            this,
            uiStateMapper = MovieListUiStateMapper(),
            stateCollector = ::collectState,
            newsCollector = ::handleNews,
        )
        store.dispatch(MovieListEvent.MovieListUiEvent.LoadData(lifecycleScope))
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
            recyclerMovies.adapter = MovieListAdapter()
            adapter = recyclerMovies.adapter as MovieListAdapter
            store.dispatch(MovieListEvent.MovieListUiEvent.LoadData(lifecycleScope))
            var previousText = ""
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.d("FRAGMENT", query)
                    store.dispatch(MovieListEvent.MovieListUiEvent.SearchClicked(
                        query,
                        lifecycleScope,
                        "?query=$query"
                    ))
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Log.d("Fragment", "$newText $previousText")
                    if (newText != previousText) {
                        previousText = newText
                        debounce.offer(newText)
                        Log.d("Fragment_list", "clear")
//                        clearData()
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

    private fun getData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.getData("").collectLatest {
                    it.map {
                        MovieToMovieItemMapper.mapMovieToMovieItem(it, requireContext())
                    }
                        .let {
                            adapter.submitData(it)
                        }
                }
            }
        }
    }

    private fun collectState(state: MovieListUiState) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.d("FRAGMENT", state.movies.toString())
                adapter.submitData(state.movies)
            }
            }
            binding.progressLoadMovies.visibility =
                if (state.progressVisibility) View.VISIBLE else View.GONE
            binding.recyclerMovies.visibility =
                if (state.listVisibility) View.VISIBLE else View.INVISIBLE

        }

    private fun handleNews(news: MovieListNews) {
        when (news) {
            is MovieListNews.NavigateTo -> {
                navigateTo(news.fragment)
            }
            is MovieListNews.ShowErrorToast -> {
                Toast.makeText(requireContext(), news.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    //
    private fun searchFilm(name: String) {
//        if (name.isEmpty())
//            getData()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.searchFilm(name, "?query=$name").collectLatest {
                    it.map {
                        MovieToMovieItemMapper.mapMovieToMovieItem(it, requireContext())
                    }.let {
                        adapter.submitData(it)
                    }
                }
            }

        }
    }


    private fun clearData() {

        adapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
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