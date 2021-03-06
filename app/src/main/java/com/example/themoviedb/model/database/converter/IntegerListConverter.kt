package com.example.themoviedb.model.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

class IntegerListConverter {
    @TypeConverter
    fun fromString(value: String): List<Int> {
        val type = object: TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromArrayList(list: List<Int>): String {
        val type = object: TypeToken<List<Int>>() {}.type
        return Gson().toJson(list, type)
    }

}
