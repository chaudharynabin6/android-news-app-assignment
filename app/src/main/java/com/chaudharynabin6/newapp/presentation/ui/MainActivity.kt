package com.chaudharynabin6.newapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaudharynabin6.newapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}