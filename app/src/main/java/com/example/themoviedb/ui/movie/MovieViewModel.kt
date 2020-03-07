package com.example.themoviedb.ui.movie

import androidx.lifecycle.MutableLiveData
import com.example.themoviedb.base.BaseViewModel
import com.example.themoviedb.model.Movie

class MovieViewModel: BaseViewModel() {
    private val movieTitle = MutableLiveData<String>()
    private val movieVoteAverage = MutableLiveData<Double>()
    private val moviePosterPath = MutableLiveData<String>()

    fun bind(movie: Movie){
        movieTitle.value = movie.title
        movieVoteAverage.value = movie.voteAverage
        moviePosterPath.value = movie.posterPath
    }

    fun getTitle():MutableLiveData<String>{
        return movieTitle
    }
    fun getAverageVote():MutableLiveData<Double>{
        return movieVoteAverage
    }

    fun getPoster():MutableLiveData<String>{
        moviePosterPath.value = "https://image.tmdb.org/t/p/w500" +  moviePosterPath.value
        return moviePosterPath
    }
}