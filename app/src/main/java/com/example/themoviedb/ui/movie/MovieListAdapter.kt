package com.example.themoviedb.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R
import com.example.themoviedb.databinding.ItemMovieBinding
import com.example.themoviedb.model.Movie

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private lateinit var movieList:List<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return if(::movieList.isInitialized) movieList.size else 0
    }

    fun updateMovieList(movieList:List<Movie>){
        this.movieList = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = MovieViewModel()

        fun bind(movie:Movie){
            viewModel.bind(movie)
            binding.viewModel = viewModel
        }
    }
}