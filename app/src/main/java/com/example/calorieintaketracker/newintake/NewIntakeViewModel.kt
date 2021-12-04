package com.example.calorieintaketracker.newintake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calorieintaketracker.FoodTime
import com.example.calorieintaketracker.database.CalorieIntake
import com.example.calorieintaketracker.database.CalorieIntakeRepository
import kotlinx.coroutines.launch

class NewIntakeViewModel ( private val repository: CalorieIntakeRepository): ViewModel() {
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

    fun onOk(
        name: String,
        calorie: Int,
        foodTime: FoodTime
    ) {
        if (name.length.equals(0))
            return


        viewModelScope.launch {
            repository.insert(CalorieIntake(name, calorie, foodTime))
            _navigateCalorieIntakeTracker.value = true
        }
    }
}