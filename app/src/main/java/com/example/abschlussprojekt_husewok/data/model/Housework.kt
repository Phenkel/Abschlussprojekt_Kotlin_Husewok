package com.example.abschlussprojekt_husewok.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class Housework(
    // Image resource ID for the housework
    val image: Int,

    // Title of the housework
    var title: String,

    // Task of the housework
    var task1: String,

    // Task of the housework
    var task2: String,

    // Task of the housework
    var task3: String,

    // Boolean if task is liked by user
    var isLiked: Boolean = true,

    // Locked days of the housework
    var lockDurationDays: Long,

    // Lock expiration date of the housework
    var lockExpirationDate: String? = null,

    val default: Boolean = true
) {
    /**
     * Checks if the lock is currently active.
     *
     * @return true if the lock is active, false otherwise.
     */
    fun isLocked(): Boolean {
        lockExpirationDate?.let {
            try {
                val lockExpirationDateAsDate = LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
                return LocalDate.now().isBefore(lockExpirationDateAsDate)
            } catch (e: DateTimeParseException) {
                // Fehler beim Parsen des Datums
                return false
            }
        }
        return false
    }

    /**
     * Updates the lock expiration date based on the current date and the lock duration.
     */
    fun updateLockExpirationDate() {
        val lockExpirationDateAsDate = LocalDate.now().plusDays(lockDurationDays)
        lockExpirationDate = lockExpirationDateAsDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}