package com.example.themoviedb.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R
import com.example.themoviedb.databinding.ItemMovieBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.viewmodel.MovieViewModel

class MovieListAdapter(private val mListener: MovieAdapterListener): RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private lateinit var movieList: MutableList<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMovieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_movie, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
        holder.itemView.setOnClickListener {
            mListener.onClickItem(movieList[position].id, movieList[position].title)
        }
    }

    override fun getItemCount(): Int {
        return if(::movieList.isInitialized) movieList.size else 0
    }

    fun updateMovieList(movieList:MutableList<Movie>){
        if (itemCount == 0) {
            this.movieList = movieList
        }
        else {
            this.movieList.addAll(movieList)
        }

        notifyDataSetChanged()
    }

    fun clearData(){
        this.movieList.clear()
        notifyDataSetChanged()
    }

    interface MovieAdapterListener {
        fun onClickItem(movieId: Int, movieTitle: String)
    }

    class ViewHolder(private val binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = MovieViewModel()

        fun bind(movie:Movie){
            viewModel.bind(movie)
            binding.viewModel = viewModel
        }
    }
}