package com.example.abschlussprojekt_husewok.data.repository

import android.util.Log
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.exampledata.HouseworkData
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.utils.Constants.firestore
import com.google.firebase.appcheck.internal.util.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

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
     *
     * @return A randomly generated ID.
     */
    private fun generateRandomId(): String {
        // Define the allowed characters for the ID
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

        // Create a StringBuilder to store the generated ID
        val builder = StringBuilder(20)

        // Generate 20 random characters and append them to the builder
        repeat(20) {
            val randomChar = allowedChars.random()
            builder.append(randomChar)
        }

        // Return the generated ID as a String
        return builder.toString()
    }

    /**
     * Updates the current user's data in Firestore.
     */
    private fun updateCurrentUserFirestore() {
        // Create a HashMap to store the updated user data
        val updatedUser = hashMapOf<String, Any?>(
            "skipCoins" to _currentUser.value?.skipCoins,
            "tasksDone" to _currentUser.value?.tasksDone,
            "tasksSkipped" to _currentUser.value?.tasksSkipped,
            "gamesWon" to _currentUser.value?.gamesWon,
            "gamesLost" to _currentUser.value?.gamesLost,
            "reward" to _currentUser.value?.reward,
            "activeHousework" to _currentUser.value?.activeHousework
        )

        _currentUser.value?.let { currentUser ->
            // Get the reference to the user document in Firestore
            val userRef = firestore.collection("user").document(currentUser.userId)

            // Update the user document with the updated user data
            userRef.set(updatedUser)
                .addOnSuccessListener {
                    Log.d(Logger.TAG, "updateCurrentUserFirebase:success")
                }
                .addOnFailureListener { exception ->
                    Log.w(Logger.TAG, "updateCurrentUserFirebase:failure", exception)
                }
        }
    }

    /**
     * Updates the current user's data from Firestore.
     *
     * @param uid The user ID.
     */
    suspend fun updateCurrentUser(uid: String) {
        // Get the reference to the user document in Firestore
        val userRef = firestore.collection("user").document(uid)

        try {
            // Get the user document and retrieve the data
            val result = userRef.get().await()
            val userData = result.data

            // Create a User object with the retrieved data
            _currentUser.value = User(
                userId = result.id,
                skipCoins = userData?.get("skipCoins").toString().toLong(),
                tasksDone = userData?.get("tasksDone").toString().toLong(),
                tasksSkipped = userData?.get("tasksSkipped").toString().toLong(),
                gamesWon = userData?.get("gamesWon").toString().toLong(),
                gamesLost = userData?.get("gamesLost").toString().toLong(),
                reward = userData?.get("reward").toString(),
                activeHousework = userData?.get("activeHousework").toString()
            )

            Log.d(Logger.TAG, "updateCurrentUserLocal:success")
        } catch (exception: Exception) {
            Log.w(Logger.TAG, "updateCurrentUserLocal:failure", exception)
        }
    }

    /**
     * Updates the housework list from Firestore.
     */
    suspend fun updateHouseworkList() {
        // Check if the current user is available
        _currentUser.value?.let { currentUser ->
            // Get the reference to the user's housework collection in Firestore
            val userHouseworkRef = firestore
                .collection("user")
                .document(currentUser.userId)
                .collection("housework")

            try {
                // Get the housework documents and retrieve the data
                val result = userHouseworkRef.get().await()
                val houseworkList = mutableListOf<Housework>()

                for (document in result) {
                    // Create a Housework object with the retrieved data
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

                    houseworkList.add(housework)
                }

                // Update the housework list
                _houseworkList.value = houseworkList

                Log.d(Logger.TAG, "updateHouseworkListLocal:success")
            } catch (exception: Exception) {
                Log.w(Logger.TAG, "updateHouseworkListLocal:failure", exception)
            }
        }
    }

    /**
     * Upserts the housework data to Firebase Firestore.
     *
     * @param housework The housework object to upsert.
     */
    fun upsertHouseworkFirebase(housework: Housework) {
        // Create a map of the updated housework data
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

        // Check if the current user is available
        _currentUser.value?.let { currentUser ->
            val updatedHouseworkId = updatedHousework["id"].toString()

            // Get the reference to the user's housework document in Firestore
            val userHouseworkRef = firestore
                .collection("user")
                .document(currentUser.userId)
                .collection("housework")
                .document(updatedHouseworkId)

            // Set the updated housework data in Firestore
            userHouseworkRef.set(updatedHousework)
                .addOnSuccessListener {
                    Log.d(Logger.TAG, "upsertHouseworkFirebase:success")
                }
                .addOnFailureListener { exception ->
                    Log.w(Logger.TAG, "upsertHouseworkFirebase:failure", exception)
                }
        }
    }

    /**
     * Creates a new user document in Firebase Firestore.
     *
     * @param uid The unique identifier of the user.
     */
    fun createNewUserFirebase(uid: String) {
        // Create a map of the new user data
        val newUser = hashMapOf(
            "gamesLost" to 0,
            "gamesWon" to 0,
            "reward" to "Joke",
            "skipCoins" to 0,
            "tasksDone" to 0,
            "tasksSkipped" to 0,
            "activeHousework" to "default_tidy_livingroom"
        )

        // Get the reference to the user document in Firestore
        val userRef = firestore.collection("user").document(uid)

        // Set the new user data in Firestore
        userRef.set(newUser)
            .addOnSuccessListener {
                Log.d(Logger.TAG, "createUserDocumentFirebase:success")
            }
            .addOnFailureListener { exception ->
                Log.w(Logger.TAG, "createUserDocumentFirebase:failure", exception)
            }

        // Update the current user value
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

        // Upsert each housework item to Firebase Firestore
        HouseworkData.houseworkList.forEach { housework ->
            upsertHouseworkFirebase(housework)
        }
    }

    /**
     * Updates the user's tasks and skip coins based on the given positive value.
     *
     * @param positive Whether the update is positive or negative.
     */
    fun updateUserTasksAndSkipCoins(positive: Boolean) {
        // Get the current user from the state
        val currentUser = _currentUser.value

        // Check if the current user is not null
        if (currentUser != null) {
            // Update the tasks done based on the positive value
            val updatedTasksDone = if (positive) {
                currentUser.tasksDone + 1
            } else {
                currentUser.tasksDone
            }

            // Update the tasks skipped based on the positive value
            val updatedTasksSkipped = if (positive) {
                currentUser.tasksSkipped
            } else {
                currentUser.tasksSkipped + 1
            }

            // Update the skip coins based on the positive value
            val updatedSkipCoins = if (positive) {
                currentUser.skipCoins + 1
            } else {
                currentUser.skipCoins - 1
            }

            // Create a new User object with the updated values
            val updatedUser = User(
                userId = currentUser.userId,
                skipCoins = updatedSkipCoins,
                tasksDone = updatedTasksDone,
                tasksSkipped = updatedTasksSkipped,
                gamesWon = currentUser.gamesWon,
                gamesLost = currentUser.gamesLost,
                reward = currentUser.reward,
                activeHousework = currentUser.activeHousework
            )

            // Update the current user state with the new User object
            _currentUser.value = updatedUser
        }

        // Update the current user in Firestore
        updateCurrentUserFirestore()
    }

    /**
     * Updates the games won and games lost of the current user.
     *
     * @param positive Whether the games won should be increased or the games lost should be increased.
     */
    fun updateUserGames(positive: Boolean) {
        // Get the current user from the state
        val currentUser = _currentUser.value

        // Check if the current user exists
        if (currentUser != null) {
            // Calculate the updated games won and games lost based on the positive parameter
            val updatedGamesWon = if (positive) {
                currentUser.gamesWon + 1
            } else {
                currentUser.gamesWon
            }

            val updatedGamesLost = if (positive) {
                currentUser.gamesLost
            } else {
                currentUser.gamesLost + 1
            }

            // Update the current user with the new games values
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
     * Updates the reward of the current user.
     * If the current reward is "Joke", it will be updated to "Bored".
     * If the current reward is "Bored", it will be updated to "Joke".
     */
    fun updateUserReward() {
        // Get the current user from the state
        val currentUser = _currentUser.value

        // Check if the current user exists
        if (currentUser != null) {
            // Calculate the updated reward based on the current reward value
            val updatedReward = if (currentUser.reward == "Joke") {
                "Bored"
            } else {
                "Joke"
            }

            // Update the current user with the new reward value
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
     * Updates the active housework based on the game result.
     *
     * @param won Whether the game was won or not.
     */
    fun updateActiveHousework(won: Boolean) {
        // Create lists to store liked and disliked housework
        val likedHousework = mutableListOf<Housework>()
        val dislikedHousework = mutableListOf<Housework>()

        // Iterate through the housework list and categorize them
        _houseworkList.value.forEach { housework ->
            if (housework.isLiked && !housework.isLocked()) {
                likedHousework.add(housework)
            } else if (!housework.isLiked && !housework.isLocked()) {
                dislikedHousework.add(housework)
            }
        }

        // Select the active housework based on the game result and availability
        try {
            _activeHousework.value = when {
                won && likedHousework.isNotEmpty() -> likedHousework.random()
                won && likedHousework.isEmpty() -> dislikedHousework.random()
                !won && dislikedHousework.isNotEmpty() -> dislikedHousework.random()
                !won && dislikedHousework.isEmpty() -> likedHousework.random()
                else -> Housework(
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
            Log.d("REPOSITORY", "updateActiveHousework:Success")
        } catch (e: Exception) {
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
            Log.w("REPOSITORY", "updateActiveHousework:Failure", e)
        }


        // Update the active housework for the current user
        _currentUser.value?.let { currentUser ->
            _currentUser.value = _activeHousework.value?.id?.let { activeHousework ->
                User(
                    userId = currentUser.userId,
                    skipCoins = currentUser.skipCoins,
                    tasksDone = currentUser.tasksDone,
                    tasksSkipped = currentUser.tasksSkipped,
                    gamesWon = currentUser.gamesWon,
                    gamesLost = currentUser.gamesLost,
                    reward = currentUser.reward,
                    activeHousework = activeHousework
                )
            }
        }

        // Update the current user in Firestore
        updateCurrentUserFirestore()
    }

    /**
     * Retrieves the active housework for the current user.
     */
    suspend fun getActiveHousework() {
        // Check if there is a current user
        _currentUser.value?.let { currentUser ->
            // Get the reference to the user document in Firestore
            val userRef = firestore
                .collection("user")
                .document(currentUser.userId)

            try {
                // Retrieve the user document from Firestore
                val result = userRef.get().await()

                // Get the active housework ID from the user document
                val activeHouseworkId = result.data?.get("activeHousework").toString()

                // Find the active housework based on the retrieved ID
                val activeHousework = _houseworkList.value.find { housework ->
                    housework.id == activeHouseworkId
                }

                // Update the active housework value
                _activeHousework.value = if (activeHousework != null) activeHousework else Housework(
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

                Log.d(Logger.TAG, "getActiveHousework:success")
            } catch (exception: Exception) {
                Log.w(Logger.TAG, "getActiveHousework:failure", exception)
            }
        }
    }

    /**
     * Sorts the housework list based on the specified criteria.
     *
     * @param sortBy The criteria to sort the housework list by.
     */
    fun sortHouseworkList(sortBy: String) {
        when (sortBy) {
            "Liked" -> {
                // Sort the housework list by liked status and then by title
                _houseworkList.value = _houseworkList.value.sortedWith(
                    compareBy({ !it.isLiked }, { it.title })
                )
            }
            "Locked" -> {
                // Sort the housework list by locked status and then by title
                _houseworkList.value = _houseworkList.value.sortedWith(
                    compareBy({ it.isLocked() }, { it.title })
                )
            }
            "Random" -> {
                // Shuffle the housework list randomly
                _houseworkList.value = _houseworkList.value.shuffled()
            }
        }
    }

    /**
     * Deletes a housework item from the Firestore database.
     *
     * @param id The ID of the housework item to delete.
     */
    fun deleteHousework(id: String) {
        _currentUser.value?.let { currentUser ->
            // Get the reference to the user's housework document
            val userHouseworkRef = firestore
                .collection("user")
                .document(currentUser.userId)
                .collection("housework")
                .document(id)

            // Delete the housework item
            userHouseworkRef.delete()
                .addOnSuccessListener {
                    Log.d(Logger.TAG, "deleteHousework:success")
                }
                .addOnFailureListener { exception ->
                    Log.w(Logger.TAG, "deleteHousework:failure", exception)
                }
        }
    }

    /**
     * Clears all user associated data.
     */
    fun logoutClearData() {
        _currentUser.value = null
        _activeHousework.value = null
        _houseworkList.value = emptyList()
    }
}