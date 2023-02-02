package com.example.patient_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_whoqol3year.*

class Whoqol_3yearActivity : AppCompatActivity() {
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

        var score1=0
        var score2=0
        var score3=0
        var score4=0
        var score5=0

        whoqolSave_btn.setOnClickListener({

            for (i in 1..26) {
                val one = resources.getIdentifier("WHOQ$i"+"_1","id", packageName)
                val two = resources.getIdentifier("WHOQ$i"+"_2","id", packageName)
                val three = resources.getIdentifier("WHOQ$i"+"_3","id", packageName)
                val four = resources.getIdentifier("WHOQ$i"+"_4","id", packageName)
                val five = resources.getIdentifier("WHOQ$i"+"_5","id", packageName)

                val radioButtonOne = findViewById<RadioButton>(one)
                val radioButtonTwo = findViewById<RadioButton>(two)
                val radioButtonThree = findViewById<RadioButton>(three)
                val radioButtonFour = findViewById<RadioButton>(four)
                val radioButtonFive = findViewById<RadioButton>(five)

                if (radioButtonOne.isChecked){
                    score1++
                }
                else if (radioButtonTwo.isChecked){
                    score2 = score2+2
                }
                else if (radioButtonThree.isChecked){
                    score3 = score3+3
                }
                else if (radioButtonFour.isChecked){
                    score4 = score4+4
                }
                else if (radioButtonFive.isChecked){
                    score5 = score5+5
                }
                else{
                    makeToast("$i 번에 응답해 주세요.")
                }
            }

            Toast.makeText(this,"저장 완료",Toast.LENGTH_SHORT).show()



        })

        whoqolNext_btn.setOnClickListener({
            var intent1 = Intent(this, MARS_3yearActivity::class.java)
            startActivity((intent1))
        })



    }
}