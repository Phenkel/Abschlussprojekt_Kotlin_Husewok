package com.example.abschlussprojekt_husewok.data.remote

/**
 * A sealed class representing the result of a network operation.
 *
 * @param T The type of data returned in the result.
 * @property data The data returned in the result.
 * @property message The message associated with the result.
 */
sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    /**
     * A success result containing the data.
     *
     * @param data The data returned in the result.
     */
    class Success<T>(data: T) : NetworkResult<T>(data)

    /**
     * An error result containing the message and optional data.
     *
     * @param message The message associated with the error.
     * @param data The data returned in the result.
     */
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

    /**
     * A loading result indicating that the operation is in progress.
     */
    class Loading<T> : NetworkResult<T>()
}