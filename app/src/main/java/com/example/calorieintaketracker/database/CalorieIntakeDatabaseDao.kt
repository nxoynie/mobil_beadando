package com.example.calorieintaketracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CalorieIntakeDatabaseDao {

    @Insert
    fun insert(calorieIntake: CalorieIntake)

    @Query("DELETE FROM calorie_intake_table")
    fun delete()

    @Query("SELECT * FROM calorie_intake_table ORDER BY foodID DESC")
    fun getAllIntakes(): LiveData<List<CalorieIntake>>

    @Query("DELETE FROM calorie_intake_table WHERE foodID = :key")
    fun deleteFood(key: Long)

    @Query("SELECT * FROM calorie_intake_table WHERE foodID = :key")
    fun get(key: Long): CalorieIntake
}