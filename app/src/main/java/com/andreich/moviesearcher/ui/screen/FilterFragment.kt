package com.andreich.moviesearcher.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.databinding.FragmentFilterBinding

class FilterFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentFilterBinding? = null
    private val binding: FragmentFilterBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    val query = mutableMapOf<String, List<String>>()

    companion object {

        const val QUERY_COUNTRY = "countries.name"
        const val QUERY_GENRE = "genres.name"
        const val QUERY_NETWORKS = "networks.items.name"
        const val QUERY_MOVIE_TYPE = "type"
        const val QUERY_YEAR = "year"
        const val QUERY_RATING = "rating.kp"

        fun newInstance(): FilterFragment {
            return FilterFragment()
        }
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
        with(binding) {
            createSpinnerAdapter(R.array.countries, spinnerCountries)
            createSpinnerAdapter(R.array.genres, spinnerGenres)
            createSpinnerAdapter(R.array.network, spinnerNetwork)
            createSpinnerAdapter(R.array.content_types, spinnerMovieTypes)
            query.clear()
            spinnerCountries.onItemSelectedListener = this@FilterFragment
            spinnerGenres.onItemSelectedListener = this@FilterFragment
            spinnerNetwork.onItemSelectedListener = this@FilterFragment
            spinnerMovieTypes.onItemSelectedListener = this@FilterFragment

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

                navigateTo(MovieListFragment.getInstance(query as java.io.Serializable))
            }
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun createSpinnerAdapter(resId: Int, spinner: Spinner) {
        ArrayAdapter.createFromResource(
            requireContext(),
            resId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
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
                query[QUERY_COUNTRY] = listOf(country)
            }
            R.id.spinnerGenres -> {
                if (position == 0) return
                val genre = parent.getItemAtPosition(position).toString().lowercase()
                query[QUERY_GENRE] = listOf(genre)
            }
            R.id.spinnerNetwork -> {
                if (position == 0) return
                val network = parent.getItemAtPosition(position).toString()
                query[QUERY_NETWORKS] = listOf(network)
            }
            R.id.spinnerMovieTypes -> {
                if (position == 0) return
                val type = parent.getItemAtPosition(position).toString()
                query[QUERY_MOVIE_TYPE] = listOf(type.movieType())
            }
        }
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        when (parent?.id) {
            R.id.spinnerCountries -> {
                query.remove(QUERY_COUNTRY)
            }
            R.id.spinnerGenres -> {
                query.remove(QUERY_GENRE)
            }
            R.id.spinnerNetwork -> {
                query.remove(QUERY_NETWORKS)
            }
            R.id.spinnerMovieTypes -> {
                query.remove(QUERY_MOVIE_TYPE)
            }
        }
    }
}