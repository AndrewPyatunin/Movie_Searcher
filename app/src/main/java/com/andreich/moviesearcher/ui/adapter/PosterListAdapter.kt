package com.andreich.moviesearcher.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Poster
import com.bumptech.glide.Glide

class PosterListDiffCallback : DiffUtil.ItemCallback<Poster>() {

    override fun areItemsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem == newItem
    }

}

class PosterListAdapter :
    ListAdapter<Poster, PosterListAdapter.PosterListViewHolder>(PosterListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.poster_item, parent, false)
        return PosterListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterListViewHolder, position: Int) {
        val poster = getItem(position)
        Log.d("PosterListAdapter", poster?.previewUrl.toString())
        with(holder) {
            Glide.with(itemView.context).load(poster?.previewUrl).into(posterImage)
        }
    }

    inner class PosterListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage = itemView.findViewById<ImageView>(R.id.poster)
    }
}