package com.example.patient_app.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File
import java.io.FileReader
import java.util.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            var file = java.io.File("/data/data/com.example.patient_app/files/ID", "id.txt")
            if(file.exists()) {
                readID(file)
                calculateSurvey()
                if(MainActivity_HR.treatFinish - MainActivity_HR.timeDiff < 1) {
                    Toast.makeText(this, "ID : ${MainActivity_HR.Patient_ID} Login", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity((intent))
                }
                else
                    Toast.makeText(this, "Survey done", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "Please create an ID", Toast.LENGTH_SHORT).show()

        }


        btn_ifNoID.setOnClickListener {
            var intent = Intent(this, Survey_1Activity::class.java)
            startActivity((intent))
        }

    }

    private fun readID(file : File){
        var fileiter = FileReader(file).readLines().iterator()
        MainActivity_HR.Patient_ID = fileiter.next()
        MainActivity_HR.treatFinish = fileiter.next().toInt()
        Log.i("Login","id : ${MainActivity_HR.Patient_ID}")
    }
    private fun calculateSurvey(){
        MainActivity_HR.treatYear = MainActivity_HR.Patient_ID[0].toString()
        var startday = MainActivity_HR.Patient_ID.substring(8)
        val cal = Calendar.getInstance()
        cal.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
        cal[Calendar.MONTH] = startday.substring(0, 2).toInt() - 1
        cal[Calendar.DATE] = startday.substring(2, 4).toInt()
        MainActivity_HR.timeDiff =
            (System.currentTimeMillis() - cal.timeInMillis) / (1000 * 60 * 60 * 24)
        Log.i("Login", "${MainActivity_HR.timeDiff.rem(7) == 0L}")
    }
}