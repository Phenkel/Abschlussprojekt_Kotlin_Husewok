package com.example.abschlussprojekt_husewok.data.model

/**
 * Data class representing a bored activity.
 *
 * @param activity The activity.
 * @param participants The number of participants required for the activity.
 * @param type The type or category of the activity.
 */
data class Bored(
    val activity: String,
    val participants: Int,
    val type: String
)