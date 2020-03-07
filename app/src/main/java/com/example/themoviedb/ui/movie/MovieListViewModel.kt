package com.example.themoviedb.ui.movie

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.example.themoviedb.R
import com.example.themoviedb.base.BaseViewModel
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.MovieDao
import com.example.themoviedb.model.Movies
import com.example.themoviedb.network.MovieApi
import javax.inject.Inject

class MovieListViewModel(private val movieDao: MovieDao):BaseViewModel(){
    @Inject
    lateinit var movieApi: MovieApi
    val movieListAdapter: MovieListAdapter = MovieListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadMovies() }

    private lateinit var subscription: Disposable

    init{
        loadMovies()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadMovies(){
        subscription = Observable.fromCallable { movieDao.all }
                .concatMap {
                    dbPostList ->
                        if(dbPostList.isEmpty())
                            movieApi.getPopularMovies().concatMap {
                                            movies ->
//                                            movieDao.insertAll(*apiMovieList.toTypedArray())
                                 Observable.just(movies)
                                       }
                        else
                            Observable.just(dbPostList)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveMovieListStart() }
                .doOnTerminate { onRetrieveMovieListFinish() }
                .subscribe(
                        { result -> onRetrieveMovieListSuccess(result as Movies) },
                        { onRetrieveMovieListError() }
                )
    }

    private fun onRetrieveMovieListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveMovieListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMovieListSuccess(movies: Movies){
        movieListAdapter.updateMovieList(movies.results)
    }

    private fun onRetrieveMovieListError(){
        errorMessage.value = R.string.post_error
    }
}