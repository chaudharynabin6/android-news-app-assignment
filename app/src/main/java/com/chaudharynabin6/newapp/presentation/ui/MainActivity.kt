package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.databinding.ActivityMainBinding
import com.chaudharynabin6.newapp.other.utils.collectLiveCycleFlow
import com.chaudharynabin6.newapp.presentation.viewmodels.NewsHeadlineViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val viewModel: NewsHeadlineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootView)
        setupBottomNavigation()
    }


    private fun setupBottomNavigation() {
        val navController = Navigation.findNavController(this@MainActivity, R.id.navHostFragment)
        binding.bottomNavigationView.setupWithNavController(navController = navController)
    }


}