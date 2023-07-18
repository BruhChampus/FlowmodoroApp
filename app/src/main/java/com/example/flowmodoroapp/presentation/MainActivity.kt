package com.example.flowmodoroapp.presentation

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import com.example.flowmodoroapp.R

import com.example.flowmodoroapp.databinding.ActivityMainBinding

 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)

    }


    //Permission for notifications
    private val requestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) {
                Toast.makeText(
                    this,
                    "OOPS, seems that you've denied permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


}