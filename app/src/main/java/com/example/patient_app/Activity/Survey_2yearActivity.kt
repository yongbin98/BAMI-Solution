package com.example.patient_app.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_survey2year.*

class Survey_2yearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey2year)


        two_YMRS_save.setOnClickListener({

            for (i in 1..5) {
                val Yes = resources.getIdentifier("YMRS1_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS1_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS1_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS1_i ="1"
                }
                else{
                    Toast.makeText(this, "1-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
        }

            for (i in 1..4) {
                val Yes = resources.getIdentifier("YMRS2_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS2_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS2_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS2_i ="1"
                }
                else{
                    Toast.makeText(this, "2-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            for (i in 1..3) {
                val Yes = resources.getIdentifier("YMRS3_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS3_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS3_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS3_i ="1"
                }
                else{
                    Toast.makeText(this, "3-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            var YMRS4_1_num = YMRS4_1_num.text.toString()
            if (YMRS4_1_num.length == 0){
                Toast.makeText(this, "4-1 항목에 응답해 주세요", Toast.LENGTH_SHORT).show()
            }



            if (YMRS4_2_yes.isChecked){
                var YMRS4_2 = "O"
            }
            else if (YMRS4_2_no.isChecked){
                var YMRS4_2 = "X"
            }
            else {
                Toast.makeText(this, "4-2 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
            }

            for (i in 1..4) {
                val Yes = resources.getIdentifier("YMRS5_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS5_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS5_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS5_i ="1"
                }
                else{
                    Toast.makeText(this, "5-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            for (i in 1..4) {
                val Yes = resources.getIdentifier("YMRS6_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS6_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS6_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS6_i ="1"
                }
                else{
                    Toast.makeText(this, "6-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            for (i in 1..6) {
                val Yes = resources.getIdentifier("YMRS7_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS7_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS7_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS7_i ="1"
                }
                else{
                    Toast.makeText(this, "7-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }


            for (i in 1..10) {
                val Yes = resources.getIdentifier("YMRS8_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS8_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS8_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS8_i ="1"
                }
                else{
                    Toast.makeText(this, "8-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }




            for (i in 1..4) {
                val Yes = resources.getIdentifier("YMRS9_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS9_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS9_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS9_i ="1"
                }
                else{
                    Toast.makeText(this, "9-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }


            for (i in 1..5) {
                val Yes = resources.getIdentifier("YMRS10_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS10_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    var YMRS10_i = "O"
                }
                else if (radioButtonNo.isChecked){
                    var YMRS10_i ="1"
                }
                else{
                    Toast.makeText(this, "10-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }


            if (YMRS11_yes.isChecked){
                var YMRS11 = "O"
            }
            else if (YMRS11_no.isChecked){
                var YMRS11 = "X"
            }
            else {
                Toast.makeText(this, "11번 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(this,"저장되었습니다.", Toast.LENGTH_SHORT).show()
        })

    }
}