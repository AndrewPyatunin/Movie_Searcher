package com.andreich.moviesearcher.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Movie
import com.bumptech.glide.Glide
import kotlin.math.round

class DiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}

class MovieListAdapter() :
    PagingDataAdapter<Movie, MovieListAdapter.MovieViewHolder>(DiffCallback()) {
    //RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var onMovieClick: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder) {
            with(movie) {
                movieTitle.text = movie?.name

                movieRating.text = movie?.rating?.let {"%.1f".format(it)}
                year.text = movie?.year.toString()
                genres.text = movie?.genres?.joinToString(", ")
                altName.text = movie?.alternativeName
                countries.text = movie?.countries.toString()
                Glide.with(holder.itemView.context).load(movie?.previewUrl ?: "")
                    .into(personAvatar)
                itemView.setOnClickListener {
                    this?.let { movie -> onMovieClick?.onMovieClick(movie) }
                }
            }
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val personAvatar = itemView.findViewById<ImageView>(R.id.movie_image)
        val year = itemView.findViewById<TextView>(R.id.movie_creation_date)
        val movieTitle = itemView.findViewById<TextView>(R.id.movie_title)
        val movieRating = itemView.findViewById<TextView>(R.id.movie_rating)
        val countries = itemView.findViewById<TextView>(R.id.movie_countries)
        val genres = itemView.findViewById<TextView>(R.id.movie_genres)
        val altName = itemView.findViewById<TextView>(R.id.movie_alt_name_with_length)
    }

    interface OnMovieClickListener {

        fun onMovieClick(movie: Movie)
    }
}