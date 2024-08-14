package com.andreich.moviesearcher.ui.adapter

import android.app.AlertDialog
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.view.menu.MenuAdapter
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.MenuProvider
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.ui.MovieItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.shimmer.ShimmerFrameLayout

object DiffCallback : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        Log.d("DIFFCALLBACK", "${oldItem.id == newItem.id}")
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }
}

class MovieListAdapter :
    PagingDataAdapter<MovieItem, MovieListAdapter.MovieViewHolder>(DiffCallback) {

    var onMovieClick: OnMovieClickListener? = null
    var onMenuClick: OnMenuItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Log.d("FRAGMENT_ADAPTER", "$position")
        val movie = getItem(position)
        movie?.let {
            with(holder) {
                movieTitle.text = movie.name
                movieRating.setBackgroundColor(movie.ratingColor)
                movieRating.text = movie.rating
                year.text = movie.year
                genres.text = movie.genres
                altName.text = movie.alternativeName
                countries.text = movie.countries
                personAvatar.visibility = View.GONE
                shimmer_item.visibility = View.VISIBLE
                Glide.with(holder.itemView.context).load(movie.previewUrl)
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            shimmer_item.visibility = View.GONE
                            personAvatar.visibility = View.VISIBLE
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

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), MenuProvider {

        val shimmer_item = itemView.findViewById<ShimmerFrameLayout>(R.id.shimmer_movie_item)
        val personAvatar = itemView.findViewById<ImageView>(R.id.movie_image)
        val year = itemView.findViewById<TextView>(R.id.movie_creation_date)
        val movieTitle = itemView.findViewById<TextView>(R.id.movie_title)
        val movieRating = itemView.findViewById<TextView>(R.id.movie_rating)
        val countries = itemView.findViewById<TextView>(R.id.movie_countries)
        val genres = itemView.findViewById<TextView>(R.id.movie_genres)
        val altName = itemView.findViewById<TextView>(R.id.movie_alt_name_with_length)
        val menuItem = itemView.findViewById<ImageView>(R.id.menu_item)

        init {
            menuItem.setOnClickListener {
                popupMenu(it)
            }
        }

        private fun popupMenu(v: View) {
            val menu = PopupMenu(itemView.context, v)
            menu.inflate(R.menu.movie_item_menu)
            val movie = snapshot().items[absoluteAdapterPosition]
            menu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_add_bookmark -> {
                        onMenuClick?.onAddBookmarkClick(movie)
                        true
                    }
                    R.id.menu_remove_bookmark -> {
                        AlertDialog.Builder(itemView.context)
                            .setTitle("Убрать \"${movie.name}\" из избранного?")
                            .setPositiveButton("Да") { dialog, _ ->
                                onMenuClick?.onRemoveBookmarkClick(movie)
                            }
                            .setNegativeButton("Отмена") {_, _ ->}
                            .create()
                            .show()


                        true
                    }
                    else -> {
                        true
                    }
                }
            }
            menu.show()
        }

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.movie_item_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

            return true
        }
    }

    interface OnMovieClickListener {

        fun onMovieClick(movie: MovieItem)
    }

    interface OnMenuItemClickListener {

        fun onAddBookmarkClick(movie: MovieItem)

        fun onRemoveBookmarkClick(movie: MovieItem)
    }
}