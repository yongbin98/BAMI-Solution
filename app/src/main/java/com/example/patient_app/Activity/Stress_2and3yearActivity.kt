package com.example.patient_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_stress2and3year.*
import kotlinx.android.synthetic.main.activity_stress2and3year.*
import kotlinx.android.synthetic.main.activity_whoqol3year.*

class Stress_2and3yearActivity : AppCompatActivity() {
    private var toast: Toast? = null
    private fun makeToast(message: String){
        try{
            toast?.cancel()
            toast = Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT)
            toast?.show()
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whoqol3year)

        var Stress_score1=0
        var Stress_score2=0
        var Stress_score3=0
        var Stress_score4=0
        var Stress_score5=0

        stressSave_btn.setOnClickListener({

            for (i in 1..26) {
                val one = resources.getIdentifier("stress$i"+"_1","id", packageName)
                val two = resources.getIdentifier("stress$i"+"_2","id", packageName)
                val three = resources.getIdentifier("stress$i"+"_3","id", packageName)
                val four = resources.getIdentifier("stress$i"+"_4","id", packageName)
                val five = resources.getIdentifier("stress$i"+"_5","id", packageName)

                val radioButtonOne = findViewById<RadioButton>(one)
                val radioButtonTwo = findViewById<RadioButton>(two)
                val radioButtonThree = findViewById<RadioButton>(three)
                val radioButtonFour = findViewById<RadioButton>(four)
                val radioButtonFive = findViewById<RadioButton>(five)

                if (radioButtonOne.isChecked){
                    Stress_score1++
                }
                else if (radioButtonTwo.isChecked){
                    Stress_score2 = Stress_score2+2
                }
                else if (radioButtonThree.isChecked){
                    Stress_score3 = Stress_score3+3
                }
                else if (radioButtonFour.isChecked){
                    Stress_score4 = Stress_score4+4
                }
                else if (radioButtonFive.isChecked){
                    Stress_score5 = Stress_score5+5
                }
                else{
                    makeToast("$i 번에 응답해 주세요.")
                }
            }

            Toast.makeText(this,"저장 완료",Toast.LENGTH_SHORT).show()



        })

        stressNext_btn.setOnClickListener({
            var intent1 = Intent(this, VAS_2and3yearActivity::class.java)
            startActivity((intent1))
        })



    }
}