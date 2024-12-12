package com.example.testapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapp.data.model.Movie
import com.example.testapp.databinding.ItemMovieBinding

class MovieAdapter(val listener: MovieAdapterInterface) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(
    DiffCallback()
){

    val newList = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (position <= newList.size -1) {
            holder.bind(newList[position])
        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.title.text = movie.Title
            binding.year.text = movie.Year
            Glide.with(binding.poster.context).load(movie.Poster).into(binding.poster)
            binding.root.setOnClickListener {
                listener.goToDetailsPage(movie.imdbID, bindingAdapterPosition)
            }
        }
    }

    fun addMoreItems(items: List<Movie>, firstPage: Boolean = false) {
        if (firstPage) {
            newList.clear()
            this.notifyDataSetChanged()
        }
        val startPos = newList.size
        newList.addAll(items)
        this.notifyItemRangeInserted(startPos,items.size)
    }

    interface MovieAdapterInterface {
        fun goToDetailsPage(imdb: String, position: Int)
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.imdbID == newItem.imdbID
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}