package com.davidmendozamartinez.movieinfo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.davidmendozamartinez.movieinfo.databinding.ItemMovieBinding

class MovieAdapter : PagingDataAdapter<MovieUI, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
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
            binding.title.text = item.title
            binding.poster.bindImageFromUrl(item.posterUrl)
        }
    }
}

class MovieDiffCallback : DiffUtil.ItemCallback<MovieUI>() {
    override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean =
        oldItem == newItem
}