package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.andreich.moviesearcher.MovieApp
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentFilterBinding
import com.andreich.moviesearcher.presentation.movie_filter.*
import com.andreich.moviesearcher.ui.state.MovieFilterUiState
import ru.tinkoff.kotea.android.lifecycle.collectOnCreate
import ru.tinkoff.kotea.android.storeViaViewModel

class MovieFilterFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    private val component by lazy { (requireActivity().application as MovieApp).component }

    val query = mutableMapOf<String, List<String>>()

    private val store by storeViaViewModel {
        MovieFilterStore(
            MovieFilterUpdate(), MovieFilterState(
                emptyMap(), emptyMap()
            )
        )
    }

    companion object {

        const val QUERY_STATE = "initial_filter_state"

        const val QUERY_COUNTRY = "countries.name"
        const val QUERY_GENRE = "genres.name"
        const val QUERY_NETWORKS = "networks.items.name"
        const val QUERY_MOVIE_TYPE = "type"
        const val QUERY_YEAR = "year"
        const val QUERY_RATING = "rating.kp"

        fun newInstance(): MovieFilterFragment {
            return MovieFilterFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        store.collectOnCreate(
            fragment = this,
            uiStateMapper = MovieFilterUiStateMapper(),
            newsCollector = ::handleNews,
            stateCollector = ::collectState
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun collectState(state: MovieFilterUiState) {
        initViews()
        with(binding) {
            spinnerNetwork.setSelection(state.networkPosition)
            spinnerMovieTypes.setSelection(state.movieTypePosition)
            spinnerCountries.setSelection(state.countryPosition)
            spinnerGenres.setSelection(state.genresPosition)
            editTextStartYear.setText(state.yearStart)
            editTextEndYear.setText(state.yearEnd)
            editTextRating.setText(state.rating)
        }
    }

    private fun handleNews(news: MovieFilterNews) {
        when (news) {
            is MovieFilterNews.NavigateTo -> {
                navigateTo(news.fragment)
            }
            is MovieFilterNews.ShowError -> {
                Toast.makeText(requireContext(), news.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            createSpinnerAdapter(R.array.countries, spinnerCountries)
            createSpinnerAdapter(R.array.genres, spinnerGenres)
            createSpinnerAdapter(R.array.network, spinnerNetwork)
            createSpinnerAdapter(R.array.content_types, spinnerMovieTypes)
            query.clear()
            spinnerCountries.onItemSelectedListener = this@MovieFilterFragment
            spinnerGenres.onItemSelectedListener = this@MovieFilterFragment
            spinnerNetwork.onItemSelectedListener = this@MovieFilterFragment
            spinnerMovieTypes.onItemSelectedListener = this@MovieFilterFragment

            buttonFilterApply.setOnClickListener {
                if (editTextStartYear.text.isNotEmpty()) {
                    query[QUERY_YEAR] =
                        if (editTextEndYear.text.isNotEmpty())
                            listOf("${editTextStartYear.text.trim()}-${editTextEndYear.text.trim()}")
                        else listOf(editTextStartYear.text.trim().toString())
                }

                if (editTextRating.text.isNotEmpty()) {
                    query[QUERY_RATING] = listOf("${editTextRating.text.trim()}-10")
                }
                store.dispatch(MovieFilterUiEvent.ApplyFilters(query))
            }
        }

    }

    private fun createSpinnerAdapter(resId: Int, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            resId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun String.movieType(): String {
        return when (this) {
            "Выберите фильм" -> ""
            "Сериал" -> "tv-series"
            "Фильм" -> "movie"
            "Аниме" -> "anime"
            "Мультфильм" -> "cartoon"
            "Анимационный сериал" -> "animated-series"
            else -> ""
        }
    }

    private fun navigateTo(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        when (parent?.id) {
            R.id.spinnerCountries -> {
                if (position == 0) return
                val country = parent.getItemAtPosition(position).toString()
                MovieFilterUiEvent.ApplyPositions(mapOf(Pair(QUERY_COUNTRY, position)))
                query[QUERY_COUNTRY] = listOf(country)
            }
            R.id.spinnerGenres -> {
                if (position == 0) return
                val genre = parent.getItemAtPosition(position).toString().lowercase()
                MovieFilterUiEvent.ApplyPositions(mapOf(Pair(QUERY_GENRE, position)))
                query[QUERY_GENRE] = listOf(genre)
            }
            R.id.spinnerNetwork -> {
                if (position == 0) return
                val network = parent.getItemAtPosition(position).toString()
                MovieFilterUiEvent.ApplyPositions(mapOf(Pair(QUERY_NETWORKS, position)))
                query[QUERY_NETWORKS] = listOf(network)
            }
            R.id.spinnerMovieTypes -> {
                if (position == 0) return
                val type = parent.getItemAtPosition(position).toString()
                MovieFilterUiEvent.ApplyPositions(mapOf(Pair(QUERY_MOVIE_TYPE, position)))
                query[QUERY_MOVIE_TYPE] = listOf(type.movieType())
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}