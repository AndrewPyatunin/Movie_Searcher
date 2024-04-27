package com.andreich.moviesearcher.ui.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.ui.MovieItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object DiffCallback : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }
}

class MovieListAdapter() :
    PagingDataAdapter<MovieItem, MovieListAdapter.MovieViewHolder>(DiffCallback) {

    var onMovieClick: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Log.d("FRAGMENT_ADAPtER", "$position")
        val movie = getItem(position)
        movie?.let {
            with(holder) {
                    movieTitle.text = movie.name
                    movieRating.setBackgroundColor (movie.ratingColor)
                    movieRating.text = movie.rating
                    year.text = movie.year
                    genres.text = movie.genres
                    altName.text = movie.alternativeName
                    countries.text = movie.countries
                    Glide.with(holder.itemView.context).load(movie.previewUrl).into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            personAvatar.setImageDrawable(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            personAvatar.setImageDrawable(placeholder)
                        }
                    })
                    itemView.setOnClickListener {
                        onMovieClick?.onMovieClick(movie)
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

        fun onMovieClick(movie: MovieItem)
    }
}