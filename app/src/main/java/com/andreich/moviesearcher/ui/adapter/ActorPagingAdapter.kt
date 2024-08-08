package com.andreich.moviesearcher.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Actor
import com.andreich.moviesearcher.domain.model.Person
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class ActorDiffCallback: DiffUtil.ItemCallback<Person>() {

    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }

}

class ActorPagingAdapter : PagingDataAdapter<Person, ActorPagingAdapter.ActorPagingViewHolder>(ActorDiffCallback()) {

    var onActorClick: OnActorPagingClickListener? = null

    inner class ActorPagingViewHolder(itemView: View): ViewHolder(itemView) {
        val personAvatar = itemView.findViewById<ImageView>(R.id.person_avatar_imageView)
        val personName = itemView.findViewById<TextView>(R.id.person_name_textView)
        val personCareer = itemView.findViewById<TextView>(R.id.person_career_textView)
        val personEnName = itemView.findViewById<TextView>(R.id.person_en_name_textView)
        val personDescription = itemView.findViewById<TextView>(R.id.person_description_textView)
        val avatarProgress = itemView.findViewById<ProgressBar>(R.id.actor_avatar_progress)
    }

    override fun onBindViewHolder(holder: ActorPagingViewHolder, position: Int) {
        val person = getItem(position)
        person?.let {
            with(holder) {
                Glide.with(holder.itemView.context).load(person.photoUrl).into(
                    object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            personAvatar.setImageDrawable(resource)
                            avatarProgress.visibility = View.GONE
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            personAvatar.setImageDrawable(placeholder)
                            avatarProgress.visibility = View.GONE
                        }

                    }
                )
                personName.text = person.name
                personEnName.text = person.enName
                personCareer.text = person.profession
                personDescription.text = ""
                itemView.setOnClickListener {
                    onActorClick?.onActorClick(person)
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorPagingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.actor_item, parent, false)
        return ActorPagingViewHolder(view)
    }

    interface OnActorPagingClickListener {

        fun onActorClick(person: Person)
    }
}

