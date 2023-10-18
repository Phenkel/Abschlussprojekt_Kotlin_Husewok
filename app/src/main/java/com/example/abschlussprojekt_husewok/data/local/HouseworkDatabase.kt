package com.example.abschlussprojekt_husewok.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussprojekt_husewok.data.model.Housework

@Database(entities = [Housework::class], version = 1)
abstract class HouseworkDatabase : RoomDatabase() {
    // Data Access Object for accessing database operations
    abstract val dao: HouseworkDao

    companion object {
        // Instance of the HouseworkDatabase
        private lateinit var dbInstance: HouseworkDatabase

        // Get an instance of the HouseworkDatabase
        fun getDatabase(context: Context): HouseworkDatabase {
            synchronized(this) {
                if (!this::dbInstance.isInitialized) {
                    dbInstance = Room.databaseBuilder(
                        context.applicationContext,
                        HouseworkDatabase::class.java,
                        "housework_database"
                    ).build()
                }
                return dbInstance
            }
        }
    }
}