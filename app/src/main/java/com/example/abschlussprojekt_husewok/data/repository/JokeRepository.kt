package com.example.abschlussprojekt_husewok.data.repository

import android.util.Log
import com.example.abschlussprojekt_husewok.data.model.Joke
import com.example.abschlussprojekt_husewok.data.remote.JokeApi
import com.example.abschlussprojekt_husewok.data.remote.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository class that provides access to the JokeAPI.
 *
 * @param jokeApi The JokeApi instance used for making API calls.
 */
class JokeRepository(private val jokeApi: JokeApi) {
    // StateFlow for the new joke
    private val _newJoke = MutableStateFlow<NetworkResult<Joke>>(NetworkResult.Loading())
    val newJoke: StateFlow<NetworkResult<Joke>>
        get() = _newJoke

    /**
     * Fetches a random joke from the JokeAPI.
     */
    suspend fun getJoke() {
        // Set the state to loading
        _newJoke.value = NetworkResult.Loading()

        try {
            // Make the API call to get a random joke
            val joke = jokeApi.retrofitService.getRandomJoke()

            // Set the state to success with the fetched joke
            _newJoke.value = NetworkResult.Success(joke)

            // Log the success message
            Log.d("JokeAPI", "getJoke:Success")
        } catch (e: Exception) {
            // Set the state to error with the exception message
            _newJoke.value = NetworkResult.Error(e.message)

            // Log the error message
            Log.w("JokeAPI", "getJoke:Failure", e)
        }
    }
}