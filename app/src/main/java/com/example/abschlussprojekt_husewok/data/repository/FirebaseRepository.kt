package com.example.abschlussprojekt_husewok.data.repository

import android.util.Log
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.exampledata.HouseworkData
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.utils.Constants
import com.google.firebase.appcheck.internal.util.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Firebase Repository class that provides access to Firebase.
 */
class FirebaseRepository {

    // Create a state flow for the current user
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?>
        get() = _currentUser

    // Create a state flow for the active housework
    private val _activeHousework = MutableStateFlow<Housework?>(null)
    val activeHousework: StateFlow<Housework?>
        get() = _activeHousework

    // Create a state flow for the housework list
    private val _houseworkList = MutableStateFlow<List<Housework>>(emptyList())
    val houseworkList: StateFlow<List<Housework>>
        get() = _houseworkList


    /**
     * Generates a random ID consisting of alphanumeric characters.
     * The length of the ID is 20 characters.
     */
    private fun generateRandomId(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        val builder = StringBuilder(20)

        repeat(20) {
            val randomChar = allowedChars.random()
            builder.append(randomChar)
        }

        return builder.toString()
    }

    /**
     * Updates the current user's data in Firestore.
     */
    private fun updateCurrentUserFirestore() {
        // Create a map of updated user data
        val updatedUser = hashMapOf<String, Any?>(
            "skipCoins" to _currentUser.value?.skipCoins,
            "tasksDone" to _currentUser.value?.tasksDone,
            "tasksSkipped" to _currentUser.value?.tasksSkipped,
            "gamesWon" to _currentUser.value?.gamesWon,
            "gamesLost" to _currentUser.value?.gamesLost,
            "reward" to _currentUser.value?.reward,
            "activeHousework" to _currentUser.value?.activeHousework
        )

        // Update the user data in Firestore
        Constants.firestore.collection("user").document(_currentUser.value?.userId.toString())
            .set(updatedUser)
            .addOnSuccessListener {
                // Log success message
                Log.d(Logger.TAG, "updateCurrentUserFirebase:success")
            }
            .addOnFailureListener {
                // Log failure message with exception
                Log.w(Logger.TAG, "updateCurrentUserFirebase:failure", it)
            }
    }

    /**
     * Updates the current user's data from Firestore.
     *
     * @param uid The unique ID of the user.
     */
    fun updateCurrentUser(uid: String) {
        // Retrieve document from the "user" collection in Firestore
        Constants.firestore.collection("user").document(uid).get()
            .addOnSuccessListener { result ->
                // Update the value of the current user with the retrieved data
                _currentUser.value = User(
                    userId = result.id,
                    skipCoins = result.data?.get("skipCoins").toString().toLong(),
                    tasksDone = result.data?.get("tasksDone").toString().toLong(),
                    tasksSkipped = result.data?.get("tasksSkipped").toString().toLong(),
                    gamesWon = result.data?.get("gamesWon").toString().toLong(),
                    gamesLost = result.data?.get("gamesLost").toString().toLong(),
                    reward = result.data?.get("reward").toString(),
                    activeHousework = result.data?.get("activeHousework").toString()
                )
                // Log success message
                Log.d(Logger.TAG, "updateCurrentUserLocal:success")
            }
            .addOnFailureListener { exception ->
                // Log failure message with exception
                Log.w(Logger.TAG, "updateCurrentUserLocal:failure", exception)
            }
    }

    /**
     * Updates the list of housework tasks for the current user.
     */
    fun updateHouseworkList() {
        _currentUser.value?.let { currentUser ->
            // Retrieve housework tasks from Firestore collection
            Constants.firestore.collection("user").document(currentUser.userId).collection("housework").get()
                .addOnSuccessListener { result ->
                    val houseworkList = mutableListOf<Housework>()

                    // Iterate through each document in the result
                    for (document in result) {
                        // Create a new Housework object with the retrieved data
                        val housework = Housework(
                            image = document.data["image"].toString().toInt(),
                            title = document.data["title"].toString(),
                            task1 = document.data["task1"].toString(),
                            task2 = document.data["task2"].toString(),
                            task3 = document.data["task3"].toString(),
                            isLiked = document.data["isLiked"].toString().toBoolean(),
                            lockDurationDays = document.data["lockDurationDays"].toString().toLong(),
                            lockExpirationDate = document.data["lockExpirationDate"].toString(),
                            default = document.data["default"].toString().toBoolean(),
                            id = document.data["id"].toString()
                        )

                        // Add the housework task to the list
                        houseworkList.add(housework)
                    }

                    // Update the housework list value
                    _houseworkList.value = houseworkList

                    // Log success message
                    Log.d(Logger.TAG, "updateHouseworkListLocal:success")
                }
                .addOnFailureListener { exception ->
                    // Log failure message with exception
                    Log.w(Logger.TAG, "updateHouseworkListLocal:failure", exception)
                }
        }
    }

    /**
     * Upserts a housework object to Firebase Firestore.
     *
     * @param housework The Housework object to upsert.
     */
    fun upsertHouseworkFirebase(housework: Housework) {
        // Create a map of updated housework data
        val updatedHousework = hashMapOf(
            "default" to housework.default,
            "id" to if (housework.id == "default") generateRandomId() else housework.id,
            "image" to housework.image,
            "isLiked" to housework.isLiked,
            "lockDurationDays" to housework.lockDurationDays,
            "lockExpirationDate" to housework.lockExpirationDate,
            "task1" to housework.task1,
            "task2" to housework.task2,
            "task3" to housework.task3,
            "title" to housework.title
        )

        // Check if the current user is not null
        _currentUser.value?.let { currentUser ->
            // Get the Firebase Firestore collection for the user's housework
            val houseworkCollection = Constants.firestore.collection("user").document(currentUser.userId).collection("housework")

            // Get the ID of the updated housework
            val updatedHouseworkId = updatedHousework["id"].toString()

            // Set the updated housework data to Firebase Firestore
            houseworkCollection.document(updatedHouseworkId).set(updatedHousework)
                .addOnSuccessListener {
                    Log.d(Logger.TAG, "upsertHouseworkFirebase:success")
                }
                .addOnFailureListener { exception ->
                    Log.w(Logger.TAG, "upsertHouseworkFirebase:failure", exception)
                }
        }
    }

    /**
     * Creates a new user document in Firebase Firestore and sets initial user data.
     * Also updates the current user object with the new user data.
     *
     * @param uid The user ID of the new user.
     */
    fun createNewUserFirebase(uid: String) {
        // Create a map of initial user data
        val newUser = hashMapOf(
            "gamesLost" to 0,
            "gamesWon" to 0,
            "reward" to "Joke",
            "skipCoins" to 0,
            "tasksDone" to 0,
            "tasksSkipped" to 0,
            "activeHousework" to "default_tidy_livingroom"
        )

        // Set the new user data to Firebase Firestore
        Constants.firestore.collection("user").document(uid).set(newUser)
            .addOnSuccessListener {
                Log.d(Logger.TAG, "createUserDocumentFirebase:success")
            }
            .addOnFailureListener { exception ->
                Log.w(Logger.TAG, "createUserDocumentFirebase:failure", exception)
            }

        // Update the current user object with the new user data
        _currentUser.value = User(
            userId = uid,
            skipCoins = 0,
            tasksDone = 0,
            tasksSkipped = 0,
            gamesWon = 0,
            gamesLost = 0,
            reward = "Joke",
            activeHousework = "default_tidy_livingroom"
        )

        // Upsert each housework from the HouseworkData.houseworkList
        HouseworkData.houseworkList.forEach { housework ->
            upsertHouseworkFirebase(housework)
        }
    }

    /**
     * Updates the skip coins of the current user.
     *
     * @param positive Boolean indicating whether to increment or decrement skip coins.
     */
    fun updateUserSkipCoins(positive: Boolean) {
        // Get the current user object
        val currentUser = _currentUser.value

        // Check if the current user object is not null
        if (currentUser != null) {
            // Update the skip coins of the current user
            val updatedSkipCoins = if (positive) currentUser.skipCoins + 1 else currentUser.skipCoins - 1
            _currentUser.value = User(
                userId = currentUser.userId,
                skipCoins = updatedSkipCoins,
                tasksDone = currentUser.tasksDone,
                tasksSkipped = currentUser.tasksSkipped,
                gamesWon = currentUser.gamesWon,
                gamesLost = currentUser.gamesLost,
                reward = currentUser.reward,
                activeHousework = currentUser.activeHousework
            )
        }

        // Update the current user in Firestore
        updateCurrentUserFirestore()
    }

    /**
     * Updates the user's tasks based on the given positive value.
     *
     * @param positive Boolean indicating whether to increment tasksDone or tasksSkipped.
     */
    fun updateUserTasks(positive: Boolean) {
        // Get the current user object
        val currentUser = _currentUser.value

        // Check if the current user object is not null
        if (currentUser != null) {
            // Update the tasks of the current user
            val updatedTasksDone = if (positive) currentUser.tasksDone + 1 else currentUser.tasksDone
            val updatedTasksSkipped = if (positive) currentUser.tasksSkipped else currentUser.tasksSkipped + 1

            _currentUser.value = User(
                userId = currentUser.userId,
                skipCoins = currentUser.skipCoins,
                tasksDone = updatedTasksDone,
                tasksSkipped = updatedTasksSkipped,
                gamesWon = currentUser.gamesWon,
                gamesLost = currentUser.gamesLost,
                reward = currentUser.reward,
                activeHousework = currentUser.activeHousework
            )
        }

        // Update the current user in Firestore
        updateCurrentUserFirestore()
    }

    /**
     * Updates the user's games based on the given positive value.
     * @param positive Boolean indicating whether to increment gamesWon or gamesLost.
     */
    fun updateUserGames(positive: Boolean) {
        // Get the current user object
        val currentUser = _currentUser.value

        // Check if the current user object is not null
        if (currentUser != null) {
            // Update the games of the current user
            val updatedGamesWon = if (positive) currentUser.gamesWon + 1 else currentUser.gamesWon
            val updatedGamesLost = if (positive) currentUser.gamesLost else currentUser.gamesLost + 1

            _currentUser.value = User(
                userId = currentUser.userId,
                skipCoins = currentUser.skipCoins,
                tasksDone = currentUser.tasksDone,
                tasksSkipped = currentUser.tasksSkipped,
                gamesWon = updatedGamesWon,
                gamesLost = updatedGamesLost,
                reward = currentUser.reward,
                activeHousework = currentUser.activeHousework
            )
        }

        // Update the current user in Firestore
        updateCurrentUserFirestore()
    }

    /**
     * Updates the user's reward.
     * If the current reward is "Joke", it updates it to "Bored".
     * If the current reward is "Bored", it updates it to "Joke".
     */
    fun updateUserReward() {
        // Get the current user object
        val currentUser = _currentUser.value

        // Check if the current user object is not null
        if (currentUser != null) {
            // Update the reward of the current user
            val updatedReward = if (currentUser.reward == "Joke") "Bored" else "Joke"

            _currentUser.value = User(
                userId = currentUser.userId,
                skipCoins = currentUser.skipCoins,
                tasksDone = currentUser.tasksDone,
                tasksSkipped = currentUser.tasksSkipped,
                gamesWon = currentUser.gamesWon,
                gamesLost = currentUser.gamesLost,
                reward = updatedReward,
                activeHousework = currentUser.activeHousework
            )
        }

        // Update the current user in Firestore
        updateCurrentUserFirestore()
    }

    /**
     * Updates the active housework based on the outcome of a game.
     *
     * @param won Boolean indicating whether the game was won or not.
     */
    fun updateActiveHousework(won: Boolean) {
        val likedHousework = mutableListOf<Housework>()
        val dislikedHousework = mutableListOf<Housework>()

        // Separate liked and disliked housework that are not locked
        _houseworkList.value.forEach { housework ->
            if (housework.isLiked && !housework.isLocked()) {
                likedHousework.add(housework)
            } else if (!housework.isLiked && !housework.isLocked()) {
                dislikedHousework.add(housework)
            }
        }

        // Update the active housework based on the game outcome and available housework
        if (won && likedHousework.isNotEmpty()) {
            _activeHousework.value = likedHousework.random()
        } else if (won && likedHousework.isEmpty()) {
            _activeHousework.value = dislikedHousework.random()
        } else if (!won && dislikedHousework.isNotEmpty()) {
            _activeHousework.value = dislikedHousework.random()
        } else if (!won && dislikedHousework.isEmpty()) {
            _activeHousework.value = likedHousework.random()
        } else {
            // Set a default housework if no suitable housework is available
            _activeHousework.value = Housework(
                image = R.drawable.img_placeholder,
                title = "All done",
                task1 = "You completed every task",
                task2 = "Just wait for new tasks",
                task3 = "Enjoy your free time",
                isLiked = true,
                lockDurationDays = 0,
                lockExpirationDate = "",
                default = true,
                id = "All done"
            )
        }
    }

    /**
     * Retrieves the active housework from Firebase Firestore based on the current user's data.
     * Sets the value of _activeHousework StateFlow.
     */
    fun getActiveHousework() {
        _currentUser.value?.let { currentUser ->
            // Get the active housework ID from the user document in Firestore
            Constants.firestore.collection("user").document(currentUser.userId)
                .get()
                .addOnSuccessListener { result ->
                    val activeHouseworkId = result.data?.get("activeHousework").toString()

                    // Find the active housework from the _houseworkList based on the ID
                    val activeHousework = _houseworkList.value.find { housework ->
                        housework.id == activeHouseworkId
                    }

                    // Set the value of _activeHousework StateFlow
                    _activeHousework.value = activeHousework
                    Log.d(Logger.TAG, "getActiveHousework:success")
                }
                .addOnFailureListener { exception ->
                    Log.w(Logger.TAG, "getActiveHousework:failure", exception)
                }
        }
    }

    /**
     * Sorts the housework list based on the given sorting criteria.
     *
     * @param sortBy The sorting criteria ("Liked", "Locked", or "Random").
     */
    fun sortHouseworkList(sortBy: String) {
        when (sortBy) {
            "Liked" -> {
                // Sort the housework list by liked status and title
                _houseworkList.value = _houseworkList.value.sortedWith(
                    compareBy({ !it.isLiked }, { it.title })
                )
            }
            "Locked" -> {
                // Sort the housework list by locked status and title
                _houseworkList.value = _houseworkList.value.sortedWith(
                    compareBy({ !it.isLocked() }, { it.title })
                )
            }
            "Random" -> {
                // Shuffle the housework list randomly
                _houseworkList.value = _houseworkList.value.shuffled()
            }
        }
    }

    /**
     * Deletes a housework item with the given ID.
     *
     * @param id The ID of the housework item to delete.
     */
    fun deleteHousework(id: String) {
        _currentUser.value?.let { currentUser ->
            val userHouseworkRef = Constants.firestore
                .collection("user")
                .document(currentUser.userId)
                .collection("housework")
                .document(id)

            userHouseworkRef.delete()
                .addOnSuccessListener {
                    Log.d(Logger.TAG, "deleteHousework:success")
                }
                .addOnFailureListener { exception ->
                    Log.w(Logger.TAG, "deleteHousework:failure", exception)
                }
        }
    }

}