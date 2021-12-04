package com.example.calorieintaketracker.intakeviewer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.calorieintaketracker.R
import com.example.calorieintaketracker.database.CalorieIntakeDatabase
import com.example.calorieintaketracker.database.CalorieIntakeRepository
import com.example.calorieintaketracker.databinding.FragmentIntakeViewerBinding

class IntakeViewerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentIntakeViewerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_intake_viewer,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = CalorieIntakeDatabase.getInstance(application).calorieIntakeDatabaseDao
        Log.d("muksz", "before args call")
        val viewModelFactory = IntakeViewerViewModelFactory(CalorieIntakeRepository(dataSource), IntakeViewerFragmentArgs.fromBundle(requireArguments()).foodID)
        Log.d("muksz", "after args call")


        val intakeViewerViewModel =
            ViewModelProvider(this, viewModelFactory).get(IntakeViewerViewModel::class.java)

        binding.intakeViewerViewModel = intakeViewerViewModel
        binding.intake = dataSource.get(IntakeViewerFragmentArgs.fromBundle(requireArguments()).foodID)

        intakeViewerViewModel.navigateToCalorieIntakeTracker.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(IntakeViewerFragmentDirections.actionIntakeViewerFragmentToCalorieIntakeTracker())
                intakeViewerViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}