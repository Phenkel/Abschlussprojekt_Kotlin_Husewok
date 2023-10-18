package com.example.abschlussprojekt_husewok.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.abschlussprojekt_husewok.data.model.User

@Dao
interface UserDao {
    // Upserts a user into the database
    @Upsert
    suspend fun insertUser(user: User)

    // Deletes all user from the database
    @Query("DELETE from user_table")
    suspend fun deleteUser()

    // Check if there is an active user in the database
    @Query("SELECT EXISTS(SELECT 1 FROM user_table LIMIT 1)")
    suspend fun hasEntries(): Boolean
}