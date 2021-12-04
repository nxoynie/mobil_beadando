package com.example.calorieintaketracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CalorieIntake::class], version= 1, exportSchema = false)
abstract class CalorieIntakeDatabase : RoomDatabase() {

    abstract val calorieIntakeDatabaseDao: CalorieIntakeDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: CalorieIntakeDatabase? = null

        fun getInstance(context: Context): CalorieIntakeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CalorieIntakeDatabase::class.java,
                        "calorie_intake_database"
                    )

                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}