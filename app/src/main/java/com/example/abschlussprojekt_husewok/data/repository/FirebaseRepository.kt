package com.example.abschlussprojekt_husewok.data.repository

import android.util.Log
import com.example.abschlussprojekt_husewok.R
import com.example.abschlussprojekt_husewok.data.exampledata.HouseworkData
import com.example.abschlussprojekt_husewok.data.model.Housework
import com.example.abschlussprojekt_husewok.data.model.User
import com.example.abschlussprojekt_husewok.utils.Constants.firestore
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.IllegalStateException

private const val FIREBASE = "FIREBASE"

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
            userRef.set(updatedUser).addOnSuccessListener {
                Log.d(FIREBASE, "updateCurrentUserFirebase:success")
            }.addOnFailureListener { exception ->
                Log.w(FIREBASE, "updateCurrentUserFirebase:failure", exception)
            }
        } ?: run {
            Log.w(FIREBASE, "updateCurrentUserFirebase:failure", IllegalStateException("User is null"))
        }
    }

    /**
     * Updates the current user data by fetching the user document from Firebase Firestore
     * and setting the value of the _currentUser StateFlow.
     *
     * @param uid The unique identifier of the user.
     * @return A [Task] that represents the asynchronous operation.
     */
    fun updateCurrentUser(uid: String): Task<String> {
        // Create a TaskCompletionSource to handle the asynchronous operation
        val src = TaskCompletionSource<String>()

        // Get the reference to the user document
        val userRef = firestore.collection("user").document(uid)

        // Fetch the user document from Firebase Firestore
        userRef.get().addOnSuccessListener { userData ->
            // Create a new User object from the fetched user data
            _currentUser.value = User(
                userId = uid,
                skipCoins = userData?.get("skipCoins").toString().toLong(),
                tasksDone = userData?.get("tasksDone").toString().toLong(),
                tasksSkipped = userData?.get("tasksSkipped").toString().toLong(),
                gamesWon = userData?.get("gamesWon").toString().toLong(),
                gamesLost = userData?.get("gamesLost").toString().toLong(),
                reward = userData?.get("reward").toString(),
                activeHousework = userData?.get("activeHousework").toString()
            )

            // Set the result of the TaskCompletionSource to "success"
            src.setResult("success")

            Log.d(FIREBASE, "updateCurrentUserLocal:success")
        }.addOnFailureListener { exception ->
            // Set the result of the TaskCompletionSource to "failure"
            src.setResult("failure")

            Log.w(FIREBASE, "updateCurrentUserLocal:failure", exception)
        }

        // Return the Task associated with the TaskCompletionSource
        return src.task
    }

    /**
     * Updates the housework list by fetching the housework items from Firebase Firestore
     * and setting the value of the _houseworkList StateFlow.
     *
     * @return A [Task] that represents the asynchronous operation.
     */
    fun updateHouseworkList(): Task<String> {
        // Create a TaskCompletionSource to handle the asynchronous operation
        val src = TaskCompletionSource<String>()

        // Check if the current user is not null
        _currentUser.value?.let { currentUser ->
            // Get the reference to the user's housework collection
            val userHouseworkRef =
                firestore.collection("user").document(currentUser.userId).collection("housework")

            // Create a mutable list to store the fetched housework items
            val houseworkList = mutableListOf<Housework>()

            // Fetch the housework items from Firebase Firestore
            userHouseworkRef.get().addOnSuccessListener { result ->
                // Iterate through the fetched documents
                for (document in result) {
                    // Create a Housework object from the document data
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
                    // Add the housework item to the list
                    houseworkList.add(housework)
                }

                // Set the value of the _houseworkList StateFlow
                _houseworkList.value = houseworkList

                // Set the result of the TaskCompletionSource to "success"
                src.setResult("success")

                Log.d(FIREBASE, "updateHouseworkListLocal:success")
            }.addOnFailureListener { exception ->
                // Set the exception of the TaskCompletionSource
                src.setException(exception)

                Log.w(FIREBASE, "updateHouseworkListLocal:failure", exception)
            }
        } ?: run {
            // Set the exception of the TaskCompletionSource
            src.setException(IllegalStateException("User is null"))

            Log.w(FIREBASE, "getActiveHousework:failure", IllegalStateException("User is null"))
        }

        // Return the Task associated with the TaskCompletionSource
        return src.task
    }

    /**
     * Upserts (updates/inserts) a housework item in Firebase Firestore.
     *
     * @param housework The housework item to be upserted.
     * @return A [Task] that represents the asynchronous operation.
     */
    fun upsertHouseworkFirebase(housework: Housework): Task<String> {
        // Create a TaskCompletionSource to handle the asynchronous operation
        val src = TaskCompletionSource<String>()

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
            // Get the ID of the updated housework
            val updatedHouseworkId = updatedHousework["id"].toString()

            // Get the reference to the user's housework document
            val userHouseworkRef =
                firestore.collection("user").document(currentUser.userId).collection("housework")
                    .document(updatedHouseworkId)

            // Upsert the updated housework data in Firebase Firestore
            userHouseworkRef.set(updatedHousework).addOnSuccessListener {
                // Set the result of the TaskCompletionSource to "success"
                src.setResult("success")

                Log.d(FIREBASE, "upsertHouseworkFirebase:success")
            }.addOnFailureListener { exception ->
                // Set the exception of the TaskCompletionSource
                src.setException(exception)

                Log.w(FIREBASE, "upsertHouseworkFirebase:failure", exception)
            }
        } ?: run {
            // Set the exception of the TaskCompletionSource
            src.setException(IllegalStateException("User is null"))

            Log.w(
                FIREBASE, "upsertHouseworkFirebase:failure", IllegalStateException("User is null")
            )
        }

        // Return the Task associated with the TaskCompletionSource
        return src.task
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
        userRef.set(newUser).addOnSuccessListener {
            Log.d(FIREBASE, "createUserDocumentFirebase:success")
        }.addOnFailureListener { exception ->
            Log.w(FIREBASE, "createUserDocumentFirebase:failure", exception)
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
     * Updates the active housework item based on the result of the task completion.
     * Selects a random housework item from the liked or disliked housework list, depending on the outcome.
     * If there are no liked or disliked housework items available, a default "All done" housework item is set.
     *
     * @param won Indicates whether the game was won or lost.
     * @return A [Task] that resolves to a string indicating the result of the update ("success" or an error message).
     */
    fun updateActiveHousework(won: Boolean): Task<String> {
        // Create a TaskCompletionSource to track the completion of the update process
        val src = TaskCompletionSource<String>()

        // Create mutable lists to store liked and disliked housework items
        val likedHousework = mutableListOf<Housework>()
        val dislikedHousework = mutableListOf<Housework>()

        // Iterate through the housework list and separate liked and disliked housework items
        _houseworkList.value.forEach { housework ->
            if (housework.isLiked && !housework.isLocked()) {
                likedHousework.add(housework)
            } else if (!housework.isLiked && !housework.isLocked()) {
                dislikedHousework.add(housework)
            }
        }

        try {
            // Update the active housework item based on the outcome and availability of liked and disliked housework
            _activeHousework.value = when {
                won && likedHousework.isNotEmpty() -> likedHousework.random()
                won && likedHousework.isEmpty() && dislikedHousework.isNotEmpty() -> dislikedHousework.random()
                !won && dislikedHousework.isNotEmpty() -> dislikedHousework.random()
                !won && dislikedHousework.isEmpty() && likedHousework.isNotEmpty() -> likedHousework.random()
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
            Log.d(FIREBASE, "updateActiveHousework:Success")
            src.setResult("success")
        } catch (e: Exception) {
            // Set a default "All done" housework item if an exception occurs during the update
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
            Log.w(FIREBASE, "updateActiveHousework:Failure", e)
            src.setException(e)
        }

        // Update the active housework item in the current user's data
        _currentUser.value?.let { currentUser ->
            _currentUser.value = _activeHousework.value?.id?.let { currentUser.copy(activeHousework = it) }
        } ?: run {
            Log.w(FIREBASE, "updateActiveHousework:failure", IllegalStateException("User is null"))
            src.setException(IllegalStateException("User is null"))
        }

        // Update the current user's data in Firestore
        updateCurrentUserFirestore()

        return src.task
    }

    /**
     * Fetches the active housework item for the current user from Firebase Firestore
     * and sets the value of the _activeHousework StateFlow.
     *
     * @return A [Task] that represents the asynchronous operation.
     */
    fun getActiveHousework(): Task<String> {
        // Create a TaskCompletionSource to handle the asynchronous operation
        val src = TaskCompletionSource<String>()

        // Check if the current user is not null
        _currentUser.value?.let { currentUser ->
            // Get the reference to the user's document in Firebase Firestore
            val userRef = firestore.collection("user").document(currentUser.userId)

            // Fetch the user document from Firebase Firestore
            userRef.get().addOnSuccessListener { result ->
                // Get the ID of the active housework from the user document
                val activeHouseworkId = result.data?.get("activeHousework").toString()

                // Find the active housework item from the _houseworkList StateFlow
                val activeHousework = _houseworkList.value.find { housework ->
                    housework.id == activeHouseworkId
                }

                // Set the value of the _activeHousework StateFlow
                _activeHousework.value = activeHousework ?: Housework(
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

                // Set the result of the TaskCompletionSource to "success"
                src.setResult("success")

                Log.d(FIREBASE, "getActiveHousework:success")
            }.addOnFailureListener { exception ->
                // Set the exception of the TaskCompletionSource
                src.setException(exception)

                Log.w(FIREBASE, "getActiveHousework:failure", exception)
            }
        } ?: run {
            // Set the exception of the TaskCompletionSource
            src.setException(IllegalStateException("User is null"))

            Log.w(FIREBASE, "getActiveHousework:failure", IllegalStateException("User is null"))
        }

        // Return the Task associated with the TaskCompletionSource
        return src.task
    }

    /**
     * Sorts the housework list based on the specified sort criteria.
     *
     * @param sortBy The sort criteria to apply to the housework list.
     * @return A [Task] that represents the asynchronous operation.
     */
    fun sortHouseworkList(sortBy: String): Task<String> {
        // Create a TaskCompletionSource to handle the asynchronous operation
        val src = TaskCompletionSource<String>()

        // Use a when statement to determine the sort criteria
        when (sortBy) {
            "Liked" -> {
                // Sort the housework list by the "isLiked" property in descending order, then by title in ascending order
                _houseworkList.value = _houseworkList.value.sortedWith(
                    compareBy({ !it.isLiked }, { it.title })
                )
                // Set the result of the TaskCompletionSource to "success"
                src.setResult("success")
            }
            "Locked" -> {
                // Sort the housework list by the "isLocked" property in ascending order, then by title in ascending order
                _houseworkList.value = _houseworkList.value.sortedWith(
                    compareBy({ it.isLocked() }, { it.title })
                )
                // Set the result of the TaskCompletionSource to "success"
                src.setResult("success")
            }
            "Random" -> {
                // Shuffle the housework list randomly
                _houseworkList.value = _houseworkList.value.shuffled()
                // Set the result of the TaskCompletionSource to "success"
                src.setResult("success")
            }
            else -> {
                // Set an exception for an invalid sort criteria
                src.setException(IllegalArgumentException("Invalid sort criteria"))
            }
        }

        // Return the Task associated with the TaskCompletionSource
        return src.task
    }

    /**
     * Deletes a housework item with the specified id from the Firestore database.
     *
     * @param id The id of the housework item to be deleted.
     * @return A task that resolves to a string indicating the result of the deletion ("success" or an error message).
     */
    fun deleteHousework(id: String): Task<String> {
        // Create a TaskCompletionSource to track the completion of the deletion process
        val src = TaskCompletionSource<String>()

        // Check if the current user is not null
        _currentUser.value?.let { currentUser ->
            // Get a reference to the housework item in the Firestore collection for the current user
            val userHouseworkRef = firestore.collection("user").document(currentUser.userId)
                .collection("housework").document(id)

            // Delete the housework item from Firestore
            userHouseworkRef.delete().addOnSuccessListener {
                // Set the result of the deletion as "success" and log a success message
                src.setResult("success")
                Log.d(FIREBASE, "deleteHousework:success")
            }.addOnFailureListener { exception ->
                // Set the exception as the result of the deletion and log a failure message
                src.setException(exception)
                Log.w(FIREBASE, "deleteHousework:failure", exception)
            }
        }

        return src.task
    }

    /**
     * Clears all user associated data.
     */
    fun logoutClearData() {
        _currentUser.value = null
        _activeHousework.value = null
        _houseworkList.value = emptyList()
    }

    /**
     * Function that adds feedback to the Firestore database.
     *
     * @param title The title of the feedback.
     * @param description The description of the feedback.
     */
    fun addFeedback(title: String, description: String) {
        // Create a HashMap to store the feedback data
        val feedback = hashMapOf(
            "title" to title, "description" to description
        )

        // Get a reference to the "feedback" collection in Firestore and generate a random document ID
        val feedbackRef = firestore.collection("feedback").document(generateRandomId())

        // Set the feedback data in the Firestore document
        feedbackRef.set(feedback)
    }
}