package com.example.themoviedb.model.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.database.converter.GenreListConverter
import com.example.themoviedb.model.database.converter.IntegerListConverter

@Database(entities = [Movie::class], version = 1)
@TypeConverters(IntegerListConverter::class, GenreListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}