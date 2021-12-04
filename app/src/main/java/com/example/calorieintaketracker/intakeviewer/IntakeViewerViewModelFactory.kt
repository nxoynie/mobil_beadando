package com.example.calorieintaketracker.intakeviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calorieintaketracker.database.CalorieIntakeRepository

class IntakeViewerViewModelFactory
    (private val repository: CalorieIntakeRepository, private val foodID: Long):
        ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IntakeViewerViewModel::class.java)) {
            return IntakeViewerViewModel(repository, foodID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}