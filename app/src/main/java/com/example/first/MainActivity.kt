package com.example.first
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Weather


        // Date
        val utilDate = Date()
        val formatType = SimpleDateFormat("yyyy년 MM월 dd일")
        date.text = formatType.format(utilDate)


        //BPM





    }

    }
