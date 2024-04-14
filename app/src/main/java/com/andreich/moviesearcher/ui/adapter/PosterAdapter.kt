package com.andreich.moviesearcher.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.model.Poster
import com.bumptech.glide.Glide

class PosterDiffCallback : DiffUtil.ItemCallback<Poster>() {

    override fun areItemsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Poster, newItem: Poster): Boolean {
        return oldItem == newItem
    }

}

class PosterAdapter() :
    PagingDataAdapter<Poster, PosterAdapter.PosterViewHolder>(PosterDiffCallback()) {
    //RecyclerView.Adapter<PosterAdapter.PosterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.poster_item, parent, false)
        return PosterViewHolder(view)
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        val poster = getItem(position)
        with(holder) {
            Glide.with(itemView.context).load(poster?.previewUrl).into(posterImage)
        }
    }

    inner class PosterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImage = itemView.findViewById<ImageView>(R.id.poster)
    }
}