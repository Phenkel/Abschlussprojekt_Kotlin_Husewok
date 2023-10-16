package com.example.abschlussprojekt_husewok.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussprojekt_husewok.data.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    // Data Access Object for accessing database operations
    abstract val dao: UserDao

    companion object {
        // Instance of the UserDatabase
        private lateinit var dbInstance: UserDatabase

        // Get an instance of the UserDatabase
        fun getDatabase(context: Context): UserDatabase {
            synchronized(this) {
                if (!this::dbInstance.isInitialized) {
                    dbInstance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "housework_database"
                    ).build()
                }
                return dbInstance
            }
        }
    }
}