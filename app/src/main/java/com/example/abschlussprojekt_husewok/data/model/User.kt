package com.example.abschlussprojekt_husewok.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    // Primary key with auto-generate
    @PrimaryKey(autoGenerate = false)
    val id: Long = 0,

    val userId: String,

    // Number of skip coins
    val skipCoins: Long,

    // Number of tasks done
    val tasksDone: Long,

    // Number of tasks skipped
    val tasksSkipped: Long,

    // Number of games won
    val gamesWon: Long,

    // Number of games lost
    val gamesLost: Long,

    val reward: String
)