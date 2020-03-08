package com.example.themoviedb.model.database.converter

import androidx.room.TypeConverter
import com.example.themoviedb.model.Genre
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

class GenreListConverter {
    @TypeConverter
    fun fromString(value: String): List<Genre> {
        val type = object: TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: List<Genre>): String {
        val type = object: TypeToken<List<Genre>>() {}.type
        return Gson().toJson(list, type)
    }

}
