package com.andreich.moviesearcher.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Review
import com.google.android.material.card.MaterialCardView

class ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}

class ReviewListAdapter() :
    PagingDataAdapter<Review, ReviewListAdapter.ReviewViewHolder>(ReviewDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        review?.let {
            Log.d("MEDIATOR_ADAPTER", review.review)
            val color = when (it.type) {
                "Позитивный" -> {
                    ContextCompat.getColor(holder.itemView.context, R.color.light_green)
                }
                "Нейтральный" -> {
                    ContextCompat.getColor(holder.itemView.context, R.color.light_gray)
                }
                "Негативный" -> {
                    ContextCompat.getColor(holder.itemView.context, R.color.light_red)
                }
                else -> {
                    ContextCompat.getColor(holder.itemView.context, R.color.light_gray)
                }
            }
            with(holder) {
                reviewTitle.text = it.title
                reviewDate.text = it.date.substring(0..9)
                reviewCard.setBackgroundColor(color)
                reviewBody.text = it.review
                reviewAuthor.text = it.author
            }
        }

    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val reviewTitle = itemView.findViewById<TextView>(R.id.review_title_textView)
        val reviewBody = itemView.findViewById<TextView>(R.id.review_body_textView)
        val reviewDate = itemView.findViewById<TextView>(R.id.review_date_textView)
        val reviewCard = itemView.findViewById<ConstraintLayout>(R.id.constraint_review)
        val reviewAuthor = itemView.findViewById<TextView>(R.id.review_author_name_textView)
    }
}