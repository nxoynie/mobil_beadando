package com.example.calorieintaketracker.newintake

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calorieintaketracker.database.CalorieIntakeRepository

class NewIntakeViewModelFactory (private val repository: CalorieIntakeRepository):
        ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewIntakeViewModel::class.java)) {
            return NewIntakeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}