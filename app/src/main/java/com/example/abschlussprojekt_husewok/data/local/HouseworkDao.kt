package com.example.abschlussprojekt_husewok.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.abschlussprojekt_husewok.data.model.Housework

@Dao
interface HouseworkDao {
    // Upserts a housework into the database
    @Upsert
    suspend fun insertHousework(housework: Housework)

    // Upserts a list of housework into the database
    @Upsert
    suspend fun insertHouseworkList(houseworkList: List<Housework>)

    // Retrieves all housework from the database
    @Query("SELECT * FROM housework_table")
    suspend fun getAllHousework(): List<Housework>

    // Deletes all housework from the database
    @Query("DELETE from housework_table")
    suspend fun deleteAllHousework()

    // Counts the number of housework in the database
    @Query("SELECT COUNT(*) FROM housework_table")
    suspend fun countHousework(): Long
}