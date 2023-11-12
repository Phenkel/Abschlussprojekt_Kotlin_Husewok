package com.example.abschlussprojekt_husewok.data.model

/**
 * Data class representing a User.
 *
 * @param userId The unique identifier of the user.
 * @param skipCoins The number of skip coins the user has.
 * @param tasksDone The number of tasks the user has completed.
 * @param tasksSkipped The number of tasks the user has skipped.
 * @param gamesWon The number of games the user has won.
 * @param gamesLost The number of games the user has lost.
 * @param reward The reward assigned to the user.
 * @param activeHousework The ID of the active housework assigned to the user.
 */
data class User(
    val userId: String,
    val skipCoins: Long,
    val tasksDone: Long,
    val tasksSkipped: Long,
    val gamesWon: Long,
    val gamesLost: Long,
    val reward: String,
    val activeHousework: String
)