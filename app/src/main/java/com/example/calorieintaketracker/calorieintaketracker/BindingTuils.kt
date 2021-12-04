package com.example.calorieintaketracker.calorieintaketracker

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.TypedValue
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.calorieintaketracker.FoodTime
import com.example.calorieintaketracker.database.CalorieIntake
import java.text.SimpleDateFormat

@BindingAdapter("foodNameFormatted")
fun TextView.setTodoTitleFormatted(item: CalorieIntake) {
    text = String.format("[%s] %s", item.foodTime, item.name)
    setBackgroundColor(
        when(item.foodTime) {
            FoodTime.BREAKFAST -> Color.BLUE
            FoodTime.LUNCH -> Color.MAGENTA
            FoodTime.DINNER -> Color.GREEN
            FoodTime.SNACK -> Color.RED
        }
    )
}

@BindingAdapter("quantityString")
fun TextView.setIntakeQuantityFormatted(item: CalorieIntake) {
    val concatenatedString = "${item.name} ${item.foodTime} ${item.intakeQuantity} kalória fogyasztva"
    text = concatenatedString
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("dateTimeString")
fun TextView.setIntakeDateTimeFormatted(item: CalorieIntake) {
    text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(item.intakeTimeMilli).toString()
}
