package com.andreich.moviesearcher.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.andreich.moviesearcher.R
import com.andreich.moviesearcher.domain.model.Person
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {

    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }

}

class ActorAdapter() :
    ListAdapter<Person, ActorAdapter.PersonViewHolder>(PersonDiffCallback()) {
    //RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.actor_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = getItem(position)
        with(holder) {
            Glide.with(itemView.context).load(person.photoUrl).into(personAvatar)
            personName.text = person.name
            personEnName.text = person.enName
            personCareer.text = person.profession
            personDescription.text = person.description
        }
    }

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personAvatar = itemView.findViewById<ImageView>(R.id.person_avatar_imageView)
        val personName = itemView.findViewById<TextView>(R.id.person_name_textView)
        val personCareer = itemView.findViewById<TextView>(R.id.person_career_textView)
        val personEnName = itemView.findViewById<TextView>(R.id.person_en_name_textView)
        val personDescription = itemView.findViewById<TextView>(R.id.person_description_textView)
    }
}