package com.example.calorieintaketracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.calorieintaketracker.FoodTime

@Entity(tableName = "calorie_intake_table")
data class CalorieIntake (

    @ColumnInfo(name = "food_name")
    var name: String,

    @ColumnInfo(name = "intake_quantity")
    var intakeQuantity: Int = -1,

    @ColumnInfo(name = "food_time")

    @TypeConverters(Converters::class)
    var foodTime: FoodTime,
    @ColumnInfo(name = "intake_time_milli")
    var intakeTimeMilli: Long = System.currentTimeMillis()
)
{
    @PrimaryKey(autoGenerate = true)
    var foodID: Long = 0L
}