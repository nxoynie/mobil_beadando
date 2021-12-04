package com.example.calorieintaketracker.calorieintaketracker

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.calorieintaketracker.database.CalorieIntake
import com.example.calorieintaketracker.database.CalorieIntakeRepository
import kotlinx.coroutines.launch

class CalorieIntakeTrackerViewModel (
    private val repository: CalorieIntakeRepository,
    application: Application) : ViewModel() {

    val intakes = repository.getAllIntake()

    val numberOfIntakes: LiveData<Int> = Transformations.map(intakes) {
        data -> data.size
    }

    private val _navigateToNewIntake= MutableLiveData<Boolean?>()
    private val _navigateToCalorieIntake= MutableLiveData<Boolean>()
    var foodID: Long = 0


    private val _submitPushed = MutableLiveData<Boolean?>()
    val submitPushed: LiveData<Boolean?>
        get() = _submitPushed

    fun doneGettingInputFromFragment() {
        _submitPushed.value = null
    }

    private val _clearPushed = MutableLiveData<Boolean?>()
    val clearPushed: LiveData<Boolean?>
        get() = _clearPushed

    fun doneHidingKeyboard() {
        _clearPushed.value = null
    }

    val navigateToNewIntake: LiveData<Boolean?>
        get() = _navigateToNewIntake

    val navigateToIntake: LiveData<Boolean?>
        get() = _navigateToCalorieIntake

    fun doneNavigating() {
        _navigateToNewIntake.value = null
        _navigateToCalorieIntake.value = null
    }


    fun onNew() {
        _navigateToNewIntake.value = true
    }

    fun onClear() {
        viewModelScope.launch {
            repository.clear()
        }
    }

    val clearButtonVisibility = Transformations.map(intakes) {
        it?.isNotEmpty()
    }


    fun onClick(calorieIntake: CalorieIntake) {
        Log.d("intake", calorieIntake.foodID.toString() + calorieIntake.toString())
        foodID = calorieIntake.foodID
        _navigateToCalorieIntake.value = true
    }

    }