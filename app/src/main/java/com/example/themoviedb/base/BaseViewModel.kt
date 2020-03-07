package com.example.themoviedb.base

//import android.arch.lifecycle.ViewModel
import androidx.lifecycle.ViewModel
import com.example.themoviedb.injection.component.DaggerViewModelInjector
import com.example.themoviedb.injection.component.ViewModelInjector
import com.example.themoviedb.injection.module.NetworkModule
import com.example.themoviedb.ui.movie.MovieListViewModel
import com.example.themoviedb.ui.movie.MovieViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MovieListViewModel -> injector.inject(this)
            is MovieViewModel -> injector.inject(this)
        }
    }
}