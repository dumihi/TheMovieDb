package com.example.themoviedb.ui.movie

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.example.themoviedb.R
import com.example.themoviedb.base.BaseViewModel
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.database.MovieDao
import com.example.themoviedb.model.Movies
import com.example.themoviedb.model.database.Resource
import com.example.themoviedb.network.MovieApi
import javax.inject.Inject

class MovieListViewModel(private val movieDao: MovieDao):BaseViewModel(){
    @Inject
    lateinit var movieApi: MovieApi
    val movieListAdapter: MovieListAdapter = MovieListAdapter()

    val movies: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    val page: MutableLiveData<Int> = MutableLiveData()
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

    fun loadMovies(page : Int = 1){
        subscription = movieApi.getPopularMovies(page)
//            Observable.fromCallable { movieDao.all }
//                .concatMap {
//                    dbPostList ->
//                        if(dbPostList.isEmpty())
//                            movieApi.getPopularMovies().concatMap {
//                                            movies ->
////                                            movieDao.insertAll(*apiMovieList.toTypedArray())
//                                 Observable.just(movies)
//                                       }
//                        else
//                            Observable.just(dbPostList)
//                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveMovieListStart() }
                .doOnTerminate { onRetrieveMovieListFinish() }
                .subscribe(
                        { result -> onRetrieveMovieListSuccess(result) },
                        { t: Throwable ->  onRetrieveMovieListError(t) }
                )
    }

    private fun onRetrieveMovieListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
        movies.value = Resource.loading()
    }

    private fun onRetrieveMovieListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMovieListSuccess(movies: Movies){
        this.page.value = movies.page
        this.movies.value = Resource.success(movies.results)
//        movieListAdapter.updateMovieList(movies.results)
    }

    private fun onRetrieveMovieListError(t: Throwable){
        errorMessage.value = R.string.post_error
        movies.value = Resource.error(t)
    }
}