package com.andreich.moviesearcher.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Episode

object EpisodeDiffCallback : DiffUtil.ItemCallback<Episode>() {

    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.name + oldItem.airDate == newItem.name + newItem.airDate
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }

}

class EpisodeAdapter : ListAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(EpisodeDiffCallback) {

    inner class EpisodeViewHolder(itemView: View) : ViewHolder(itemView) {

        val textViewTitle = itemView.findViewById<TextView>(R.id.text_view_episode_title)
        val textViewNumber = itemView.findViewById<TextView>(R.id.text_view_episode_number)
        val textViewDate = itemView.findViewById<TextView>(R.id.text_view_episode_date)
        val textViewDescription =
            itemView.findViewById<TextView>(R.id.text_view_episode_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.episode_item, parent, false)
        return EpisodeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = currentList[position]
        with(holder) {
            textViewDate.text = episode.airDate.take(10)
            textViewDescription.text = episode.description
            textViewNumber.text = episode.number.toString()
            textViewTitle.text = episode.name
        }
    }
}