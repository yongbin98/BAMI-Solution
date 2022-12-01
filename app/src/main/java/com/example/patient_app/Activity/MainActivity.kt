package com.example.patient_app.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(){
    private val TAG = "Main"
    private val myCoroutinescope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_survey.findViewById<Button>(R.id.btn_survey)
        btn_survey.setOnClickListener({
            val intent = Intent(this, Survey_1Activity::class.java)
            startActivity((intent))
        })

//        btn1.setOnClickListener {
//            Log.i(TAG, "btn1 On clicked")
//            BleService.connect(this)
//        }
//
//        btn2.setOnClickListener {
//            Log.i(TAG, "btn2 On clicked")
//            APIService.connect(this){
//                Log.i(TAG,it.toString())
//                true
//            }
//
//        }
    }

}