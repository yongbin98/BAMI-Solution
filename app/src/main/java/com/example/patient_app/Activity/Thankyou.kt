package com.example.patient_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_thankyou.*

class Thankyou : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou)

        end_btn.setOnClickListener({
            var intent1 = Intent(this, MainActivity::class.java)
            startActivity((intent1))
        })




        }

    }