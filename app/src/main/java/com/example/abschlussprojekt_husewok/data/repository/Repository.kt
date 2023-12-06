package com.example.abschlussprojekt_husewok.data.repository

import com.example.abschlussprojekt_husewok.data.model.Bored
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.Joke
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi
import com.example.abschlussprojekt_husewok.data.remote.NetworkResult
import com.google.android.gms.tasks.Task
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

    suspend fun getReward(): Task<String> {
        return if (currentUser.value?.reward == "Joke") {
            joke.getJoke()
        } else {
            bored.getBored()
        }
    }
}