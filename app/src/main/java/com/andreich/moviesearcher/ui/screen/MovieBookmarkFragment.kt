package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentMovieBookmarkBinding
import com.andreich.moviesearcher.presentation.movie_bookmark.MovieBookmarkEvent
import com.andreich.moviesearcher.presentation.movie_bookmark.MovieBookmarkNews
import com.andreich.moviesearcher.presentation.movie_bookmark.MovieBookmarkStore
import com.andreich.moviesearcher.presentation.movie_bookmark.MovieBookmarkUiStateMapper
import com.andreich.moviesearcher.ui.MovieItem
import com.andreich.moviesearcher.ui.adapter.MovieListAdapter
import com.andreich.moviesearcher.ui.state.MovieBookmarkUiState
import kotlinx.coroutines.launch
import ru.tinkoff.kotea.android.lifecycle.collectOnCreate
import ru.tinkoff.kotea.android.storeViaViewModel
import javax.inject.Inject

class MovieBookmarkFragment : Fragment() {

    companion object {

        fun getInstance(): MovieBookmarkFragment {
            return MovieBookmarkFragment()
        }
    }

    private val component by lazy { (activity?.application as MovieApp).component }

    private var _binding: FragmentMovieBookmarkBinding? = null
    private val binding: FragmentMovieBookmarkBinding
        get() = _binding ?: throw RuntimeException("MovieBookmarkBinding is null!")

    @Inject
    lateinit var bookmarkStore: MovieBookmarkStore

    private val movieAdapter by lazy(LazyThreadSafetyMode.NONE) { MovieListAdapter() }

    private val store by storeViaViewModel { bookmarkStore }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        store.collectOnCreate(
            this,
            MovieBookmarkUiStateMapper(),
            ::collectState,
            ::handleNews
        )
        store.dispatch(MovieBookmarkEvent.MovieBookmarkUiEvent.LoadMovies)
        movieAdapter.setFragmentTag(this.javaClass.simpleName)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerBookmarkMovie.adapter = movieAdapter
        binding.recyclerBookmarkMovie.layoutManager = LinearLayoutManager(context)

        movieAdapter.onMovieClick = object : MovieListAdapter.OnMovieClickListener {
            override fun onMovieClick(movie: MovieItem) {
                store.dispatch(
                    MovieBookmarkEvent.MovieBookmarkUiEvent.NavigateTo(
                        MovieDetailFragment.getInstance(movie.id)
                    )
                )
            }

        }

        movieAdapter.onMenuClick = object : MovieListAdapter.OnMenuItemClickListener {
            override fun onAddBookmarkClick(movie: MovieItem) {
            }

            override fun onRemoveBookmarkClick(movie: MovieItem) {
                store.dispatch(MovieBookmarkEvent.MovieBookmarkUiEvent.RemoveBookmark(movie.id, movie.name))
            }

        }
    }

    private fun collectState(state: MovieBookmarkUiState) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                state.movies.let {
                    movieAdapter.submitData(PagingData.from(it))
                }
            }
        }
        state.progressVisibility.let {

        }
    }

    private fun handleNews(news: MovieBookmarkNews) {
        when (news) {
            is MovieBookmarkNews.NavigateTo -> {
                navigateTo(news.fragment)
            }
            is MovieBookmarkNews.ShowError -> {
                Toast.makeText(context, news.message, Toast.LENGTH_SHORT).show()
            }
            is MovieBookmarkNews.ShowResult -> {
                Toast.makeText(requireContext(), news.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}