package com.chaudharynabin6.newapp.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.chaudharynabin6.newapp.R
import com.chaudharynabin6.newapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootView)

        setupBottomNavigation()
    }




    private fun setupBottomNavigation(){
        val  navController = Navigation.findNavController(this@MainActivity, R.id.navHostFragment)
        binding.bottomNavigationView.setupWithNavController(navController = navController)
    }
}