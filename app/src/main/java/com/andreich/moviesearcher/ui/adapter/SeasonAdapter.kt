package com.andreich.moviesearcher.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Season

object SeasonDiffCallback : DiffUtil.ItemCallback<Season>() {

    override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
        return oldItem == newItem
    }
}

class SeasonAdapter : ListAdapter<Season, SeasonAdapter.SeasonViewHolder>(SeasonDiffCallback) {

    inner class SeasonViewHolder(itemView: View) : ViewHolder(itemView) {
        val textViewSeasonNumber = itemView.findViewById<TextView>(R.id.text_view_season_title)
        val textViewSeasonData = itemView.findViewById<TextView>(R.id.text_view_season_data)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.season_recycler)

        init {
            recyclerView.adapter = EpisodeAdapter()
            recyclerView.layoutManager = LinearLayoutManager(itemView.context, VERTICAL, false)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.season_item, parent, false)
        return SeasonViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = currentList[position]
        with(holder) {
            textViewSeasonNumber.text = "Сезон ${season.number}"
            textViewSeasonData.text = "${season.airDate.take(10)}, эпизодов: ${season.episodesCount}"
            (recyclerView.adapter as EpisodeAdapter).submitList(season.episodes)
        }
    }
}