package com.example.patient_app_watch

import android.app.Activity
import android.os.Bundle
import com.example.patient_app_watch.databinding.ActivityWatchMainBinding

class watchMain : Activity() {

    private lateinit var binding: ActivityWatchMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWatchMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}