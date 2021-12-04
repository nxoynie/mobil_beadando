package com.example.calorieintaketracker.calorieintaketracker

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calorieintaketracker.database.CalorieIntakeRepository

class CalorieIntakeTrackerViewModelFactory(
    private val repository: CalorieIntakeRepository,
    private val application: Application ) :
    ViewModelProvider.Factory  {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalorieIntakeTrackerViewModel::class.java)) {
            return CalorieIntakeTrackerViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
