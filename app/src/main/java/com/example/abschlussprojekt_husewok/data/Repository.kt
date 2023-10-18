package com.example.abschlussprojekt_husewok.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import com.example.abschlussprojekt_husewok.data.local.HouseworkDatabase
import com.example.abschlussprojekt_husewok.data.local.UserDatabase
import com.example.abschlussprojekt_husewok.data.model.Bored
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.Joke
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi

class Repository(boredApi: BoredApi, jokeApi: JokeApi, houseworkDb: HouseworkDatabase, userDb: UserDatabase) {


}