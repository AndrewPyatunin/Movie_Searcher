package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentMovieListBinding
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.ui.Debounce
import com.andreich.moviesearcher.ui.MovieListViewModel
import com.andreich.moviesearcher.ui.ViewModelFactory
import com.andreich.moviesearcher.ui.adapter.MovieListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListFragment : Fragment() {

    companion object {

        fun getInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }

    private var _binding: FragmentMovieListBinding? = null
    private val binding: FragmentMovieListBinding get() = _binding ?: throw RuntimeException("Binding is null!")

    private val component by lazy { (activity?.application as MovieApp).component }
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var adapter: MovieListAdapter

    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java] }

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
        with(binding) {
            recyclerMovies.layoutManager = LinearLayoutManager(requireContext())
            recyclerMovies.adapter = MovieListAdapter()
            adapter = recyclerMovies.adapter as MovieListAdapter
            getData()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchFilm(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Debounce(1000, lifecycleScope) {
                        searchFilm(newText)
                    }.offer(newText)
                    return true
                }

            })
            adapter.onMovieClick = object : MovieListAdapter.OnMovieClickListener {
                override fun onMovieClick(movie: Movie) {
                    navigateTo(MovieDetailFragment.getInstance(movie))
                }
            }
        }
    }

    private fun getData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getData().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun searchFilm(name: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchFilm(name).collectLatest {
                adapter.submitData(it)
            }
        }
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