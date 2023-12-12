package com.example.abschlussprojekt_husewok.data.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Data class representing a housework task.
 *
 * @param image The resource ID of the image associated with the task.
 * @param title The title or name of the task.
 * @param task1 The first task description.
 * @param task2 The second task description.
 * @param task3 The third task description.
 * @param isLiked Indicates whether the task is liked or not.
 * @param lockDurationDays The duration of the lock in days.
 * @param lockExpirationDate The expiration date of the lock in ISO_LOCAL_DATE format.
 * @param default Indicates whether the task is a default task or not.
 * @param id The ID of the task.
 */
data class Housework(
    val image: Int,
    val title: String,
    val task1: String,
    val task2: String,
    val task3: String,
    val isLiked: Boolean,
    val lockDurationDays: Long,
    val lockExpirationDate: String,
    val default: Boolean,
    val id: String
) {
    /**
     * Checks if the housework task is locked based on the lock expiration date.
     *
     * @return True if the task is locked, false otherwise.
     */
    fun isLocked(): Boolean {
        return try {
            val lockExpirationDateAsDate = LocalDate.parse(lockExpirationDate, DateTimeFormatter.ISO_LOCAL_DATE)
            LocalDate.now().isBefore(lockExpirationDateAsDate)
        } catch (e: DateTimeParseException) {
            false
        }
    }

    /**
     * Updates the lock expiration date by adding the lock duration days to the current date.
     */
    fun updateLockExpirationDate(): String {
        val lockExpirationDateAsDate = LocalDate.now().plusDays(lockDurationDays)
        return lockExpirationDateAsDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }
}