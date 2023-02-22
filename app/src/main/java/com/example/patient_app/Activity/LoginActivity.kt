package com.example.patient_app.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_login.*
import java.io.FileReader

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {

            var file = java.io.File("/data/data/com.example.patient_app/files/ID", "id.txt")
            if(file.exists()) {
                MainActivity_HR.Patient_ID = FileReader(file).readLines().iterator().next()
                Log.i("Login","id : ${MainActivity_HR.Patient_ID}")
                Toast.makeText(this, "ID : ${MainActivity_HR.Patient_ID} Login", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity((intent))
            }
            else
                Toast.makeText(this, "Please create an ID", Toast.LENGTH_SHORT).show()

        }

        btn_ifNoID.setOnClickListener {
            var intent = Intent(this, Survey_1Activity::class.java)
            startActivity((intent))
        }

    }
}