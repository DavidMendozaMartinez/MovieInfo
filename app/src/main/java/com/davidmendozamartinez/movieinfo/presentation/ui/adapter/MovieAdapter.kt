package com.davidmendozamartinez.movieinfo.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidmendozamartinez.movieinfo.databinding.ItemMovieBinding
import com.davidmendozamartinez.movieinfo.presentation.model.MovieUI

class MovieAdapter(private val clickListener: (View, Int) -> Unit) :
    PagingDataAdapter<MovieUI, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { movie ->
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                clickListener(it, movie.id)
            }
        }
    }

    class ViewHolder private constructor(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: MovieUI) {
            binding.movie = item
            binding.poster.contentDescription = item.title
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<MovieUI>() {
    override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean =
        oldItem == newItem
}