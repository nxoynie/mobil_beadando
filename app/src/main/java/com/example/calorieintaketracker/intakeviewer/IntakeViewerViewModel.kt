package com.example.calorieintaketracker.intakeviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorieintaketracker.database.CalorieIntakeRepository
import kotlinx.coroutines.launch

class IntakeViewerViewModel (private val repository: CalorieIntakeRepository, val foodID: Long): ViewModel(){

    private val _navigateCalorieIntakeTracker = MutableLiveData<Boolean?>()

    val navigateToCalorieIntakeTracker: LiveData<Boolean?>
        get() = _navigateCalorieIntakeTracker

    fun doneNavigating() {
        _navigateCalorieIntakeTracker.value = null
    }

    fun onCancel() {
        viewModelScope.launch {
            _navigateCalorieIntakeTracker.value = true
        }
    }

    fun onDone() {
        viewModelScope.launch {
            repository.deleteByKey(foodID)
            _navigateCalorieIntakeTracker.value = true
        }
    }
}