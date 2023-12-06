package com.example.abschlussprojekt_husewok.data.repository

import android.util.Log
import com.example.abschlussprojekt_husewok.data.model.Bored
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.NetworkResult
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository class that provides access to the BoredAPI.
 *
 * @param boredApi The BoredApi instance used for making API calls.
 */
class BoredRepository(private val boredApi: BoredApi) {
    // StateFlow for the new bored
    private val _newBored = MutableStateFlow<NetworkResult<Bored>>(NetworkResult.Loading())
    val newBored: StateFlow<NetworkResult<Bored>>
        get() = _newBored

    suspend fun getBored(): Task<String> {
        val task = TaskCompletionSource<String>()
        _newBored.value = NetworkResult.Loading()
        try {
            val bored = boredApi.retrofitService.getRandomBored()
            _newBored.value = NetworkResult.Success(bored)
            task.setResult("success")
            Log.d("BoredAPI", "getJoke:Success")
        } catch (e: Exception) {
            _newBored.value = NetworkResult.Error(e.message)
            task.setException(e)
            Log.w("BoredAPI", "getJoke:Failure", e)
        }
        return task.task
    }
}