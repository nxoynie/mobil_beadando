package com.example.calorieintaketracker.newintake

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.calorieintaketracker.FoodTime
import com.example.calorieintaketracker.R
import com.example.calorieintaketracker.database.CalorieIntakeDatabase
import com.example.calorieintaketracker.database.CalorieIntakeRepository
import com.example.calorieintaketracker.databinding.FragmentNewIntakeBinding

class NewIntakeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewIntakeBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_intake,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        val dataSource = CalorieIntakeDatabase.getInstance(application).calorieIntakeDatabaseDao
        val viewModelFactory = NewIntakeViewModelFactory(CalorieIntakeRepository(dataSource))

        val newIntakeViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(NewIntakeViewModel::class.java)

        binding.newIntakeViewModel = newIntakeViewModel

        newIntakeViewModel.navigateToCalorieIntakeTracker.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(NewIntakeFragmentDirections.actionNewIntakeFragmentToCalorieIntakeTracker())
                newIntakeViewModel.doneNavigating()
            }
        })

        binding.foodtime.adapter =
            context?.let { ArrayAdapter(it,  android.R.layout.simple_list_item_1, FoodTime.values()) }

        binding.okButton.setOnClickListener {
            val name = binding.foodName.text.toString()
            val calorie = binding.foodCalorie.text.toString().toInt()
            val foodTime = binding.foodtime.selectedItem as FoodTime

            newIntakeViewModel.onOk(name, calorie, foodTime)
            hideKeyboard()

        }

        return binding.root



    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}