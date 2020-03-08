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
data class Genre(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String
)
