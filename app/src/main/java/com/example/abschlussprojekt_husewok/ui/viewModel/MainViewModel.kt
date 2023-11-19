package com.example.abschlussprojekt_husewok.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.abschlussprojekt_husewok.data.repository.Repository
import com.example.abschlussprojekt_husewok.data.model.Housework
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
    val joke = repository.newJoke
    val bored = repository.newBored

    // State Flow for tracking the first loading state
    private var _firstLoading = MutableStateFlow(true)
    val firstLoading: StateFlow<Boolean>
        get() = _firstLoading

    /**
     * Sets the first loading state to false.
     */
    fun firstLoaded() {
        _firstLoading.value = false
    }

    /**
     * Updates the current user's data from Firestore.
     *
     * @param uid The user ID.
     */
    suspend fun updateCurrentUser(uid: String) {
        repository.firebase.updateCurrentUser(uid)
    }

    /**
     * Updates the detailed housework information.
     *
     * @param housework The housework object containing the updated information.
     */
    fun updateDetailedHousework(housework: Housework) {
        repository.updateDetailedHousework(housework)
    }

    /**
     * Updates the housework list from Firestore.
     */
    suspend fun updateHouseworkList() {
        repository.firebase.updateHouseworkList()
    }

    /**
     * Upserts the housework data to Firebase Firestore.
     *
     * @param housework The housework object to upsert.
     */
    fun upsertHouseworkFirebase(housework: Housework) {
        repository.firebase.upsertHouseworkFirebase(housework)
    }

    /**
     * Creates a new user document in Firebase Firestore.
     *
     * @param uid The unique identifier of the user.
     */
    fun createNewUserFirebase(uid: String) {
        repository.firebase.createNewUserFirebase(uid)
    }

    /**
     * Updates the reward of the current user.
     * If the current reward is "Joke", it will be updated to "Bored".
     * If the current reward is "Bored", it will be updated to "Joke".
     */
    fun updateUserReward() {
        repository.firebase.updateUserReward()
    }

    /**
     * Updates the tasks of the current user.
     *
     * @param positive Whether the tasks done should be increased or the tasks skipped should be increased.
     */
    fun updateUserTasksAndSkipCoins(positive: Boolean) {
        repository.firebase.updateUserTasksAndSkipCoins(positive)
    }

    /**
     * Updates the games won and games lost of the current user.
     *
     * @param positive Whether the games won should be increased or the games lost should be increased.
     */
    fun updateUserGames(positive: Boolean) {
        repository.firebase.updateUserGames(positive)
    }

    /**
     * Sorts the housework list based on the specified criteria.
     *
     * @param sortBy The criteria to sort the housework list by.
     */
    fun sortHouseworkList(sortBy: String) {
        repository.firebase.sortHouseworkList(sortBy)
    }

    /**
     * Updates the active housework based on the result of a game.
     *
     * @param won Whether the player won the game or not.
     */
    fun updateActiveHousework(won: Boolean) {
        repository.firebase.updateActiveHousework(won)
    }

    /**
     * Retrieves the active housework for the current user.
     */
    suspend fun getActiveHousework() {
        repository.firebase.getActiveHousework()
    }

    /**
     * Deletes a housework item from the Firestore database.
     *
     * @param id The ID of the housework item to delete.
     */
    fun deleteHousework(id: String) {
        repository.firebase.deleteHousework(id)
    }

    /**
     * Retrieves a reward based on the user's current reward type.
     * If the reward type is "Joke", a joke is retrieved. Otherwise, a random activity is retrieved.
     */
    suspend fun getReward() {
        repository.getReward()
    }

    /**
     * Clears all user associated data.
     */
    fun logoutClearData() {
        repository.firebase.logoutClearData()
    }
}