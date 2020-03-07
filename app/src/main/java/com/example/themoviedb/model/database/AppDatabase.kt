package com.example.themoviedb.model.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.MovieDao
import com.example.themoviedb.model.database.converter.IntegerListConverter

@Database(entities = [Movie::class], version = 1)
@TypeConverters(IntegerListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao():  MovieDao
}

//@Database(entities = [Post::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun postDao(): PostDao
//}