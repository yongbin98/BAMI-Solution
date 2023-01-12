package com.example.patient_app.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_survey3year.*

class Survey_3yearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey3year)

        val Nolist = mutableListOf(1,2,3,4,5,6,9,10)
        val Yeslist = mutableListOf(7,8)
        var MARSscore = 0

        three_MARS_save_btn.setOnClickListener({
            for (i in Nolist) {
                val Yes = resources.getIdentifier("MARS$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("MARS$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    MARSscore=MARSscore
                }
                else if (radioButtonNo.isChecked){
                    MARSscore++
                }
                else{
                    Toast.makeText(this, "1-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            for (i in Yeslist) {
                val Yes = resources.getIdentifier("MARS$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("MARS$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    MARSscore++
                }
                else if (radioButtonNo.isChecked){
                    MARSscore=MARSscore
                }
                else{
                    Toast.makeText(this, "1-$i 항목에 응답해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            MARS_score.text = "총점  $MARSscore   "





        })





















    }
}