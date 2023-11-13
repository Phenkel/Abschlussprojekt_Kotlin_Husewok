package com.example.abschlussprojekt_husewok.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.abschlussprojekt_husewok.data.repository.Repository
import com.example.abschlussprojekt_husewok.data.model.Housework

/**
 * ViewModel class for the app.
 *
 * @param repository The repository.
 */
class MainViewModel(
    /*application: Application*/
    private val repository: Repository
) : ViewModel() {
    // State Flows for accessing data from the repository
    val currentUser = repository.currentUser
    val detailedHousework = repository.detailedHousework
    val activeHousework = repository.activeHousework
    val houseworkList = repository.houseworkList

    suspend fun updateCurrentUser(uid: String) {
        repository.firebase.updateCurrentUser(uid)
    }

    fun updateDetailedHousework(housework: Housework) {
        repository.updateDetailedHousework(housework)
    }

    suspend fun updateHouseworkList() {
        repository.firebase.updateHouseworkList()
    }

    fun upsertHouseworkFirebase(housework: Housework) {
        repository.firebase.upsertHouseworkFirebase(housework)
    }

    suspend fun createNewUserFirebase(uid: String) {
        repository.firebase.createNewUserFirebase(uid)
    }

    fun updateUserReward() {
        repository.firebase.updateUserReward()
    }

    fun updateUserTasks(positive: Boolean) {
        repository.firebase.updateUserTasks(positive)
    }

    fun updateUserGames(positive: Boolean) {
        repository.firebase.updateUserGames(positive)
    }

    fun updateUserSkipCoins(positive: Boolean) {
        repository.firebase.updateUserSkipCoins(positive)
    }

    fun sortHouseworkList(sortBy: String) {
        repository.firebase.sortHouseworkList(sortBy)
    }

    suspend fun updateActiveHousework(won: Boolean) {
        repository.firebase.updateActiveHousework(won)
    }

    suspend fun getActiveHousework() {
        repository.firebase.getActiveHousework()
    }

    fun deleteHousework(id: String) {
        repository.firebase.deleteHousework(id)
    }

}