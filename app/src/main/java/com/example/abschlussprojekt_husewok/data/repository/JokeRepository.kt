package com.example.abschlussprojekt_husewok.data.repository

import android.util.Log
import com.example.abschlussprojekt_husewok.data.model.Joke
import com.example.abschlussprojekt_husewok.data.remote.JokeApi
import com.example.abschlussprojekt_husewok.data.remote.NetworkResult
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
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


    suspend fun getJoke(): Task<String> {
        val task = TaskCompletionSource<String>()
        _newJoke.value = NetworkResult.Loading()
        try {
            val joke = jokeApi.retrofitService.getRandomJoke()
            _newJoke.value = NetworkResult.Success(joke)
            task.setResult("success")
            Log.d("JokeAPI", "getJoke:Success")
        } catch (e: Exception) {
            _newJoke.value = NetworkResult.Error(e.message)
            task.setException(e)
            Log.w("JokeAPI", "getJoke:Failure", e)
        }
        return task.task
    }
}