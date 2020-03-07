package com.example.themoviedb.base

//import android.arch.lifecycle.ViewModel
import androidx.lifecycle.ViewModel
import com.example.themoviedb.injection.component.DaggerViewModelInjector
import com.example.themoviedb.injection.component.ViewModelInjector
import com.example.themoviedb.injection.module.NetworkModule
import com.example.themoviedb.ui.post.PostListViewModel
import com.example.themoviedb.ui.post.PostViewModel

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
            is PostListViewModel -> injector.inject(this)
            is PostViewModel -> injector.inject(this)
        }
    }
}