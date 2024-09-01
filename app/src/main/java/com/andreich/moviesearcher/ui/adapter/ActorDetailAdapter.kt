package com.andreich.moviesearcher.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Movies

object ActorDetailDiffCallback : DiffUtil.ItemCallback<Movies>() {

    override fun areItemsTheSame(
        oldItem: Movies,
        newItem: Movies
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Movies,
        newItem: Movies
    ): Boolean {
        return oldItem == newItem
    }
}

class ActorDetailAdapter :
    ListAdapter<Movies, ActorDetailAdapter.ActorDetailViewHolder>(ActorDetailDiffCallback) {

    var onCLick: ((Movies) -> Unit)? = null

    inner class ActorDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.text_view_movie_name)
        val altName = itemView.findViewById<TextView>(R.id.text_view_alt_name_with_date)
        val rating = itemView.findViewById<TextView>(R.id.text_view_rating)
        val description = itemView.findViewById<TextView>(R.id.text_view_actor_description)

        init {
            itemView.setOnClickListener {
                onCLick?.invoke(currentList[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.actor_detail_movie_item, parent, false)
        return ActorDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorDetailViewHolder, position: Int) {
        val item = getItem(position)
        with(holder) {
            name.text = item.name
            altName.text = item.alternativeName
            rating.text = item.rating.toString()
            rating.setTextColor(item.ratingColor ?: 0)
            description.text = item.description
        }

    }
}