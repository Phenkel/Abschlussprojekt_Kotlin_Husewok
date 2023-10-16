package com.example.abschlussprojekt_husewok.data

import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.local.HouseworkDatabase
import com.example.abschlussprojekt_husewok.data.local.UserDatabase
import com.example.abschlussprojekt_husewok.data.model.Bored
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.Joke
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Repository(boredApi: BoredApi, jokeApi: JokeApi, houseworkDb: HouseworkDatabase, userDb: UserDatabase) {

    // StateFlows for random Bored
    private val _randomBored = MutableStateFlow<Bored>(Bored("", 0, ""))
    val randomBored: StateFlow<Bored>
        get() = _randomBored

    // StateFlows for random Joke
    private val _randomJoke = MutableStateFlow<Joke>(Joke("", "", ""))
    val randomJoke: StateFlow<Joke>
        get() = _randomJoke

    // StateFlow for active User
    private val _activeUser = MutableStateFlow<User>(User())
    val activeUser: StateFlow<User>
        get() = _activeUser

    // StateFlow for active Housework
    private val _activeHousework = MutableStateFlow<Housework?>(null)
    val activeHousework: StateFlow<Housework?>
        get() = _activeHousework

    // StateFlow for HouseWork List
    private val _houseworkList = MutableStateFlow<List<Housework>>(listOf())
    val houseworkList: StateFlow<List<Housework>>
        get() = _houseworkList
}