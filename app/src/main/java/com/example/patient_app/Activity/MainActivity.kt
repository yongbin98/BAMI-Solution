package com.example.patient_app.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.patient_app.Bluetooth.bleRepository
import com.example.patient_app.R
import com.example.patient_app.WeatherAPI.WeatherAPI
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){
    private val TAG = "Main"
    private val myCoroutinescope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainlayout)

        val testbtnOn = findViewById<Button>(R.id.testbtn)

        runBlocking {

        }

        testbtnOn.setOnClickListener(){
            Log.i(TAG,"testbtn On clicked")
            val intent = Intent(this@MainActivity,bleRepository::class.java)
            startActivity(intent)
//            val wAPI = WeatherAPI()
//            wAPI.setInit()
//            myCoroutinescope.launch {
//                val job1 = myCoroutinescope.async {
//                    wAPI.runAPI()
//                }
//                job1.await()
//
//            }
        }
    }


}