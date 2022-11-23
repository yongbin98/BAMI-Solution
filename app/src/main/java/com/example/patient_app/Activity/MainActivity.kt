package com.example.patient_app.Activity

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCallback
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.patient_app.bluetooth.BleRepository
import com.example.patient_app.R
import com.example.patient_app.bluetooth.BleService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(){
    private val TAG = "Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainlayout)


        val testbtnOn = findViewById<Button>(R.id.testbtn)
        val discntbtn = findViewById<Button>(R.id.backbtn)

        testbtnOn.setOnClickListener() {
            Log.i(TAG, "cntbtn On clicked")
            BleService.connect(this)
//            val intent = Intent(this@MainActivity,BleRepository::class.java)
//            startActivity(intent)
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

        discntbtn.setOnClickListener() {
            Log.i(TAG, "discntbtn On clicked")
            BleService.write("Asd\n")
//            BleService.disconnect()
        }

    }


}