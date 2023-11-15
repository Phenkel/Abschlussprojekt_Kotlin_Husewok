package com.example.abschlussprojekt_husewok.data.repository

import com.example.abschlussprojekt_husewok.data.model.Bored
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.Joke
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi
import com.example.abschlussprojekt_husewok.data.remote.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository class that provides access to various data sources.
 *
 * @param boredApi The BoredApi instance for accessing bored activities.
 * @param jokeApi The JokeApi instance for accessing jokes.
 */
class Repository(boredApi: BoredApi, jokeApi: JokeApi) {
    // Create an instance of the Firebase repository
    val firebase = FirebaseRepository()

    // Create an instance of the Bored Repository
    val bored = BoredRepository(boredApi)

    // Create an instance of the Joke Repository
    val joke = JokeRepository(jokeApi)

    // Get the current user state flow from the Firebase repository
    val currentUser: StateFlow<User?>
        get() = firebase.currentUser

    // Get the active housework state flow from the Firebase repository
    val activeHousework: StateFlow<Housework?>
        get() = firebase.activeHousework

    // Get the housework list state flow from the Firebase repository
    val houseworkList: StateFlow<List<Housework>>
        get() = firebase.houseworkList

    // Get the bored state flow from the Bored repository
    val newBored: StateFlow<NetworkResult<Bored>>
        get() = bored.newBored

    // Get the joke state flow from the Joke repository
    val newJoke: StateFlow<NetworkResult<Joke>>
        get() = joke.newJoke

    // Create a state flow for the detailed housework
    private val _detailedHousework = MutableStateFlow<Housework?>(null)
    val detailedHousework: StateFlow<Housework?>
        get() = _detailedHousework

    /**
     * Updates the detailed housework information.
     *
     * @param housework The housework object containing the updated information.
     */
    fun updateDetailedHousework(housework: Housework) {
        _detailedHousework.value = housework
    }

    /**
     * Retrieves a reward based on the user's current reward type.
     * If the reward type is "Joke", a joke is retrieved. Otherwise, a random activity is retrieved.
     */
    suspend fun getReward() {
        if (currentUser.value?.reward == "Joke") {
            // Retrieve a joke if the reward type is "Joke"
            joke.getJoke()
        } else {
            // Retrieve a random activity if the reward type is not "Joke"
            bored.getBored()
        }
    }
}