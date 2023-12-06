package com.example.abschlussprojekt_husewok.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.abschlussprojekt_husewok.data.repository.Repository
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.google.android.gms.tasks.Task
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
    fun updateCurrentUser(uid: String): Task<String> {
        return repository.firebase.updateCurrentUser(uid)
    }

    /**
     * Updates the housework list from Firestore.
     */
    fun updateHouseworkList(): Task<String> {
        return repository.firebase.updateHouseworkList()
    }

    /**
     * Upserts the housework data to Firebase Firestore.
     *
     * @param housework The housework object to upsert.
     */
    fun upsertHouseworkFirebase(housework: Housework): Task<String> {
         return repository.firebase.upsertHouseworkFirebase(housework)
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
    fun sortHouseworkList(sortBy: String): Task<String> {
        return repository.firebase.sortHouseworkList(sortBy)
    }

    /**
     * Updates the active housework based on the result of a game.
     *
     * @param won Whether the player won the game or not.
     */
    fun updateActiveHousework(won: Boolean): Task<String> {
        return repository.firebase.updateActiveHousework(won)
    }

    /**
     * Retrieves the active housework for the current user.
     */
    fun getActiveHousework(): Task<String> {
        return repository.firebase.getActiveHousework()
    }

    /**
     * Deletes a housework item from the Firestore database.
     *
     * @param id The ID of the housework item to delete.
     */
    fun deleteHousework(id: String): Task<String> {
        return repository.firebase.deleteHousework(id)
    }

    /**
     * Retrieves a reward based on the user's current reward type.
     * If the reward type is "Joke", a joke is retrieved. Otherwise, a random activity is retrieved.
     */
    suspend fun getReward(): Task<String> {
        return repository.getReward()
    }

    /**
     * Clears all user associated data.
     */
    fun logoutClearData() {
        repository.firebase.logoutClearData()
        _firstLoading.value = true
    }

    /**
     * Function that adds feedback to the Firestore database.
     *
     * @param title The title of the feedback.
     * @param description The description of the feedback.
     */
    fun addFeedback(title: String, description: String) {
        repository.firebase.addFeedback(title, description)
    }
}