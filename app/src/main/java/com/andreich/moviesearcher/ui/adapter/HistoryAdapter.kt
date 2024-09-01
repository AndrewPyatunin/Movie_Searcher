package com.andreich.moviesearcher.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.MovieSearchHistory

object HistoryDiffCallback : DiffUtil.ItemCallback<MovieSearchHistory>() {

    override fun areItemsTheSame(
        oldItem: MovieSearchHistory,
        newItem: MovieSearchHistory
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieSearchHistory,
        newItem: MovieSearchHistory
    ): Boolean {
        return oldItem == newItem
    }
}

class HistoryAdapter :
    ListAdapter<MovieSearchHistory, HistoryAdapter.HistoryViewHolder>(HistoryDiffCallback) {

    var onCLick: ((MovieSearchHistory) -> Unit)? = null

    inner class HistoryViewHolder(itemView: View) : ViewHolder(itemView) {
        val textTitle = itemView.findViewById<TextView>(R.id.history_text)

        init {
            itemView.setOnClickListener {
                onCLick?.invoke(currentList[absoluteAdapterPosition])
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("HISTORY_TEXT", item.movieTitle)
        holder.textTitle.text = item.movieTitle
    }
}