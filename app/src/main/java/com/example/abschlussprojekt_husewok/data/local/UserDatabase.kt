package com.example.abschlussprojekt_husewok.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.abschlussprojekt_husewok.data.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "product_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}