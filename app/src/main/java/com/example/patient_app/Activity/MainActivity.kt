package com.example.patient_app.Activity

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.Patient_APP.databinding.MainlayoutBinding
import com.example.patient_app.WeatherAPI.APIService
import com.example.patient_app.WeatherAPI.GPStransfer
import com.example.patient_app.WeatherAPI.WeatherAPI
import com.example.patient_app.bluetooth.BleService
import kotlinx.coroutines.*
import java.util.Locale

class MainActivity : AppCompatActivity(){
    private val TAG = "Main"
    private val myCoroutinescope = CoroutineScope(Dispatchers.IO)
    private lateinit var binding : MainlayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = MainlayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        btn_survey.setOnClickListener({
//            val intent = Intent(this, Survey_1Activity::class.java)
//            startActivity((intent))
//        })

        binding.btn1.setOnClickListener {
            Log.i(TAG, "btn1 On clicked")
            BleService.connect(this)
        }

        binding.btn2.setOnClickListener {
            Log.i(TAG, "btn2 On clicked")
            APIService.connect(this){
                Log.i(TAG,it.toString())
                true
            }

        }
    }

}