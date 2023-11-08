package com.example.abschlussprojekt_husewok.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abschlussprojekt_husewok.data.repository.Repository
import com.example.abschlussprojekt_husewok.data.local.UserDatabase
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.data.remote.BoredApi
import com.example.abschlussprojekt_husewok.data.remote.JokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    private val repository = Repository(BoredApi, JokeApi, UserDatabase.getInstance(application))

    val currentUser = repository.currentUser

    val detailedHousework = repository.detailedHousework

    val activeHousework = repository.activeHousework

    val houseworkList = repository.houseworkList

    fun updateCurrentUser(uid: String) {
        repository.updateCurrentUser(uid)
    }

    fun updateCurrentUserFirestore() {
        repository.updateCurrentUserFirestore()
    }

    fun changeRewardCurrentUser() {
        repository.changeRewardCurrentUser()
    }

    fun updateDetailedHousework(housework: Housework) {
        repository.updateDetailedHousework(housework)
    }

    fun updateHouseworkList() {
        repository.updateHouseworkList()
    }
}