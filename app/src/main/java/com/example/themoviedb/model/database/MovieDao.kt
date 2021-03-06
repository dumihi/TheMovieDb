package com.example.themoviedb.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.themoviedb.model.Movie

@Dao
interface MovieDao {
    @get:Query("SELECT * FROM movie")
    val all: List<Movie>

    @Insert
    fun insertAll(vararg users: Movie)
}