package com.example.abschlussprojekt_husewok.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    // Primary key with auto-generate
    @PrimaryKey(autoGenerate = false)
    val id: Long = 1,

    // Number of skip coins
    val skipCoins: Long = 0,

    // Number of tasks done
    val tasksDone: Long = 0,

    // Number of tasks skipped
    val tasksSkipped: Long = 0,

    // Number of games won
    val gamesWons: Long = 0,

    // Number of games lost
    val gamesLost: Long = 0
)