package com.example.themoviedb.model.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.themoviedb.model.Post
import com.example.themoviedb.model.PostDao

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}