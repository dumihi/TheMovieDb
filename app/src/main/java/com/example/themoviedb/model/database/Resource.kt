package com.example.themoviedb.model.database


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val throwable: Throwable? = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data)
        }

        fun <T> error(throwable: Throwable): Resource<T> {
            return Resource(status = Status.ERROR, throwable = throwable)
        }

        fun <T> loading(): Resource<T> {
            return Resource(status = Status.LOADING)
        }
    }
}

/**
 * Status of a resource that is provided to the UI.
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}