package com.example.themoviedb.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.themoviedb.model.database.AppDatabase
import com.example.themoviedb.viewmodel.MovieListViewModel
import com.example.themoviedb.viewmodel.MovieViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "movies").build()
            @Suppress("UNCHECKED_CAST")
            return MovieListViewModel() as T
        }
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "movies").build()
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}