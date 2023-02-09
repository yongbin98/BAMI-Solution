package com.example.patient_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_mars3year.*
import kotlinx.android.synthetic.main.activity_mars3year.*

class MARS_3yearActivity : AppCompatActivity() {
    private var toast: Toast? = null
    private fun makeToast(message: String){
        try{
            toast?.cancel()
            toast = Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT)
            toast?.show()
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mars3year)

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
                    Mars.Mars[i-1] = "0점"
                }
                else if (radioButtonNo.isChecked){
                    MARSscore++
                    Mars.Mars[i-1] = "1점"
                }
                else{
                    makeToast("$i 항목에 응답해 주세요.")
                }
            }

            for (i in Yeslist) {
                val Yes = resources.getIdentifier("MARS$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("MARS$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    MARSscore++
                    Mars.Mars[i-1] = "1점"
                }
                else if (radioButtonNo.isChecked){
                    MARSscore=MARSscore
                    Mars.Mars[i-1] = "0점"
                }
                else{
                    makeToast("$i 항목에 응답해 주세요.")
                }
            }

            Toast.makeText(this,"저장 완료",Toast.LENGTH_SHORT).show()

            Mars.Score = "$MARSscore 점"
        })


        marsNext_btn.setOnClickListener({
            var intent1 = Intent(this, Hamilton_3yearActivity::class.java)
            startActivity((intent1))
        })
    }





    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}