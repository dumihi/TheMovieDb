package com.example.themoviedb.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.themoviedb.R
import com.example.themoviedb.base.BaseViewModel
import com.example.themoviedb.model.Movie
import com.example.themoviedb.network.MovieApi
import com.example.themoviedb.utils.URL_POSTER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieViewModel : BaseViewModel() {
    @Inject
    lateinit var movieApi: MovieApi
    private val movieTitle = MutableLiveData<String>()
    val movieDuration = MutableLiveData<Int>()
    val movieGenres = MutableLiveData<String>()
    val movieOverview = MutableLiveData<String>()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    private val movieVoteAverage = MutableLiveData<Double>()
    private val moviePosterPath = MutableLiveData<String>()

    private lateinit var subscription: Disposable

    val errorClickListener = View.OnClickListener {
        //        loadMovieById()
    }

    // bind if has existing movie
    fun bind(movie: Movie) {
        movieTitle.value = movie.title
        movieVoteAverage.value = movie.voteAverage
        moviePosterPath.value =  URL_POSTER + movie.posterPath
    } 

    // load movie by Id
    fun loadMovieById(id: Int = 1) {
        subscription = movieApi.getMovieDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMovieStart() }
            .doOnTerminate { onRetrieveMovieFinish() }
            .subscribe(
                { result -> onRetrieveMovieSuccess(result) },
                { t: Throwable -> onRetrieveMovieError(t) }
            )
    }

    private fun onRetrieveMovieError(t: Throwable) {
        errorMessage.value = R.string.post_error
        Log.d(this@MovieViewModel.toString(), t.message)
    }

    private fun onRetrieveMovieSuccess(result: Movie?) {
        movieTitle.value = result?.title
        movieVoteAverage.value = result?.voteAverage
        moviePosterPath.value = URL_POSTER + result?.posterPath
        movieDuration.value = result?.getDuration()
        movieGenres.value = result?.getStringGenresList()
        movieOverview.value = result?.overview
    }

    private fun onRetrieveMovieFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMovieStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }


    fun getTitle(): MutableLiveData<String> {
        return movieTitle
    }

    fun getAverageVote(): MutableLiveData<Double> {
        return movieVoteAverage
    }

    fun getPoster(): MutableLiveData<String> {
        return moviePosterPath
    }

}