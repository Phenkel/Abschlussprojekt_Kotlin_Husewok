package com.example.abschlussprojekt_husewok.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.example.abschlussprojekt_husewok.data.Repository
import com.example.abschlussprojekt_husewok.data.local.HouseworkDatabase
import com.example.abschlussprojekt_husewok.data.local.UserDatabase
import com.example.abschlussprojekt_husewok.data.model.Bored
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.Joke
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi

class MainViewModel(application: Application, state: SavedStateHandle) : AndroidViewModel(application) {
    private val repository = Repository(BoredApi, JokeApi, HouseworkDatabase.getDatabase(application), UserDatabase.getDatabase(application))

    // State for random Bored
    var randomBored by mutableStateOf<Bored?>(null)
        private set

    // State for random Joke
    var randomJoke by mutableStateOf<Joke?>(null)
        private set

    // State for active Housework
    var activeHousework by mutableStateOf<Housework?>(null)
        private set

    // State for HouseWork List
    var houseworkList by mutableStateOf<List<Housework>?>(null)
        private set
}