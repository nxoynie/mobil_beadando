package com.example.calorieintaketracker.calorieintaketracker

import android.widget.ExpandableListView
import com.example.calorieintaketracker.database.CalorieIntake

class OnCalorieIntakeListener(val clickListener: (calorieIntake: CalorieIntake) -> Unit) {
    fun onClick(calorieIntake: CalorieIntake) =clickListener(calorieIntake)
}