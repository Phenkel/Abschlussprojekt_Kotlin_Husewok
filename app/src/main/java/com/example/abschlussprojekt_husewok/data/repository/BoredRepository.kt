package com.example.abschlussprojekt_husewok.data.repository

import android.util.Log
import com.example.abschlussprojekt_husewok.data.model.Bored
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.NetworkResult
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

    /**
     * Fetches a random bored from the BoredAPI.
     */
    suspend fun getBored() {
        // Set the state to loading
        _newBored.value = NetworkResult.Loading()

        try {
            // Make the API call to get a random bored
            val bored = boredApi.retrofitService.getRandomBored()

            // Set the state to success with the fetched bored
            _newBored.value = NetworkResult.Success(bored)

            // Log the success message
            Log.d("BoredAPI", "getJoke:Success")
        } catch (e: Exception) {
            // Set the state to error with the exception message
            _newBored.value = NetworkResult.Error(e.message)

            // Log the error message
            Log.w("BoredAPI", "getJoke:Failure", e)
        }
    }
}