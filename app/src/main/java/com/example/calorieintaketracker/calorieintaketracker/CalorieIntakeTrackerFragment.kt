package com.example.calorieintaketracker.calorieintaketracker

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.calorieintaketracker.R
import com.example.calorieintaketracker.database.CalorieIntakeDatabase
import com.example.calorieintaketracker.database.CalorieIntakeRepository
import com.example.calorieintaketracker.databinding.FragmentCalorieIntakeTrackerBinding

class CalorieIntakeTrackerFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCalorieIntakeTrackerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_calorie_intake_tracker,
            container,
            false
        )
        val application = requireNotNull(this.activity).application

        val dataSource = CalorieIntakeDatabase.getInstance(application).calorieIntakeDatabaseDao

        val viewModelFactory = CalorieIntakeTrackerViewModelFactory(CalorieIntakeRepository(dataSource), application)

        val calorieIntakeTrackerViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CalorieIntakeTrackerViewModel::class.java)

        binding.calorieIntakeTrackerViewModel = calorieIntakeTrackerViewModel

        val adapter = CalorieIntakeAdapter(OnCalorieIntakeListener {
            calorieIntake -> calorieIntakeTrackerViewModel.onClick(calorieIntake)
        })
        binding.intakeList.adapter = adapter


        calorieIntakeTrackerViewModel.intakes.observe(viewLifecycleOwner, {
            it?.let {
                adapter.addHeaderSubmitList(it)
            }
        })


        calorieIntakeTrackerViewModel.navigateToIntake.observe(viewLifecycleOwner, {
            if (it == true) {
                Log.d("muksz", "navigateToIntake.observe")

                this.findNavController().navigate(CalorieIntakeTrackerFragmentDirections.actionCalorieIntakeTrackerToIntakeViewerFragment(calorieIntakeTrackerViewModel.foodID))

                Log.d("muksz", calorieIntakeTrackerViewModel.foodID.toString())

                Log.d("muksz", "navigateToCalorieIntake.observe after navi")

                calorieIntakeTrackerViewModel.doneNavigating()
            }
        })

        calorieIntakeTrackerViewModel.numberOfIntakes.observe(viewLifecycleOwner, {
            binding.numberOfIntakes.text = String.format("%s intake(s) left", it)
        })

        binding.setLifecycleOwner(this)

        calorieIntakeTrackerViewModel.navigateToNewIntake.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(CalorieIntakeTrackerFragmentDirections.actionCalorieIntakeTrackerToNewIntakeFragment())
                calorieIntakeTrackerViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}