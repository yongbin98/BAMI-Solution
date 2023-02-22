package com.example.patient_app.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener({
            var intent1 = Intent(this, MainActivity::class.java)
            startActivity((intent1))
            finish()
                                })

        btn_ifNoID.setOnClickListener({
            var intent1 = Intent(this, Survey_1Activity::class.java)
            startActivity((intent1))
                finish()
        })

    }
}