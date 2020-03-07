package com.example.themoviedb.model

import androidx.room.Entity
import com.squareup.moshi.Json
import java.util.ArrayList

/**
 * Class which provides a model for video
 * @constructor Sets all properties of the video
 * @property id the unique identifier of the video
 * ..... more property
 */
@Entity(tableName = "Movie", primaryKeys = ["id"])
data class Movie(
    @field:Json(name = "adult") val adult: Boolean,
    @field:Json(name = "poster_path") val posterPath: String,
    @field:Json(name = "overview") val overview: String,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "genre_ids") val genreIds: List<Int> = ArrayList(),
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "originalTitle") val originalTitle: String,
    @field:Json(name = "original_language") val originalLanguage: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "backdrop_path") val backdropPath: String,
    @field:Json(name = "popularity") val popularity: Double,
    @field:Json(name = "vote_count") val voteCount: Int,
    @field:Json(name = "video") val video: Boolean,
    @field:Json(name = "vote_average") val voteAverage: Double
)
