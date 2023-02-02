package com.example.patient_app.Activity

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
                }
                else if (radioButtonNo.isChecked){
                    MARSscore++
                }
                else{
                    makeToast("1-$i 항목에 응답해 주세요.")
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
                    makeToast("1-$i 항목에 응답해 주세요.")
                }
            }

            MARS_score.text = "총점  $MARSscore   "





        })
    }

    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}