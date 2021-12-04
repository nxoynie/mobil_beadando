package com.example.calorieintaketracker.database

import androidx.lifecycle.LiveData

class CalorieIntakeRepository(private val calorieIntakeDatabaseDao: CalorieIntakeDatabaseDao) {

    suspend fun insert(calorieIntake: CalorieIntake) = calorieIntakeDatabaseDao.insert(calorieIntake)

    suspend fun deleteByKey(key: Long) = calorieIntakeDatabaseDao.deleteFood(key)

    suspend fun clear() = calorieIntakeDatabaseDao.delete()

    fun getAllIntake(): LiveData<List<CalorieIntake>> = calorieIntakeDatabaseDao.getAllIntakes()

    fun getFoodByKey(key: Long): CalorieIntake = calorieIntakeDatabaseDao.get(key)
}