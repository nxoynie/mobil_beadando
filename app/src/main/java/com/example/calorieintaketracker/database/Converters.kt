package com.example.calorieintaketracker.database

import androidx.room.TypeConverter
import com.example.calorieintaketracker.FoodTime

class Converters {
    @TypeConverter
    fun intToPriority(value: Int) = enumValues<FoodTime>()[value]

    @TypeConverter
    fun priorityToInt(value: FoodTime) = value.ordinal
}