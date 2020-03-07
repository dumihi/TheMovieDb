package com.example.themoviedb.model

import androidx.room.Entity
import com.squareup.moshi.Json

/**
 * Class which provides a model for video
 * @constructor Sets all properties of the video
 * @property id the unique identifier of the video
 * ..... more property
 */
data class Movies(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "results") val results: List<Movie>,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)

