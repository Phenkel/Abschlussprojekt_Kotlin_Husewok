package com.example.abschlussprojekt_husewok.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.abschlussprojekt_husewok.data.Repository
import com.example.abschlussprojekt_husewok.data.local.HouseworkDatabase
import com.example.abschlussprojekt_husewok.data.local.UserDatabase
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository(BoredApi, JokeApi, HouseworkDatabase.getDatabase(application), UserDatabase.getDatabase(application))

    // StateFlows for random bored, random joke, active user, active housework, and housework list
    val randomBored = repository.randomBored
    val randomJoke = repository.randomJoke
    val activeUser = repository.activeUser
    val activeHousework = repository.activeHousework
    val houseworkList = repository.houseworkList
}