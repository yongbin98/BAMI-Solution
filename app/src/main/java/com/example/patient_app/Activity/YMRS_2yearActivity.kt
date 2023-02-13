package com.example.patient_app.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_ymrs2year.*

class YMRS_2yearActivity : AppCompatActivity() {
    private var toast: Toast? = null
    private fun makeToast(message: String){
        try{
            toast?.cancel()
            toast = Toast.makeText(this,message,Toast.LENGTH_SHORT)
            toast?.show()
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ymrs2year)

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()


        two_YMRS_Next_btn.setOnClickListener({

            editor.putBoolean("isToastShown",false)
            editor.apply()

            for (i in 1..5) {
                val Yes = resources.getIdentifier("YMRS1_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS1_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS1[i-1] = "O"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS1[i-1] ="X"
                }
                else{
                    makeToast("1-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
        }

            for (i in 1..4) {
                val Yes = resources.getIdentifier("YMRS2_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS2_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS2[i-1] = "O"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS2[i-1] ="X"
                }
                else{
                    makeToast("2-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }

            for (i in 1..3) {
                val Yes = resources.getIdentifier("YMRS3_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS3_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS3[i-1] = "0"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS3[i-1] ="1"
                }
                else{
                    makeToast("3-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }

            YMRSanswer.YMRS4[0] = YMRS4_1_num.text.toString()
            if (YMRSanswer.YMRS4[0].length == 0){
                makeToast("4-1 항목에 응답해 주세요")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }



            if (YMRS4_2_yes.isChecked){
                YMRSanswer.YMRS4[1] = "O"
            }
            else if (YMRS4_2_no.isChecked){
                YMRSanswer.YMRS4[1] = "X"
            }
            else {
                makeToast("4-2 항목에 응답해 주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            for (i in 1..4) {
                val Yes = resources.getIdentifier("YMRS5_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS5_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS5[i-1] = "O"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS5[i-1] ="X"
                }
                else{
                    makeToast("5-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }

            for (i in 1..4) {
                val Yes = resources.getIdentifier("YMRS6_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS6_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS6[i-1] = "O"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS6[i-1] ="1"
                }
                else{
                    makeToast("6-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }

            for (i in 1..6) {
                val Yes = resources.getIdentifier("YMRS7_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS7_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS7[i-1] = "O"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS7[i-1] ="X"
                }
                else{
                    makeToast("7-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }


            for (i in 1..10) {
                val Yes = resources.getIdentifier("YMRS8_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS8_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS8[i-1] = "O"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS8[i-1] ="X"
                }
                else{
                    makeToast("8-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }




            for (i in 1..4) {
                val Yes = resources.getIdentifier("YMRS9_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS9_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS9[i-1] = "O"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS9[i-1] ="X"
                }
                else{
                    makeToast("9-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }


            for (i in 1..5) {
                val Yes = resources.getIdentifier("YMRS10_$i"+"_yes","id", packageName)
                val No = resources.getIdentifier("YMRS10_$i"+"_no","id", packageName)
                val radioButtonYes = findViewById<RadioButton>(Yes)
                val radioButtonNo = findViewById<RadioButton>(No)

                if (radioButtonYes.isChecked){
                    YMRSanswer.YMRS10[i-1] = "O"
                }
                else if (radioButtonNo.isChecked){
                    YMRSanswer.YMRS10[i-1] ="X"
                }
                else{
                    makeToast("10-$i 항목에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }


            if (YMRS11_yes.isChecked){
                YMRSanswer.YMRS11 = "O"
            }
            else if (YMRS11_no.isChecked){
                YMRSanswer.YMRS11 = "X"
            }
            else {
                makeToast("11번 항목에 응답해 주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if(!sharedPreferences.getBoolean("isToastShown",false)){
                var intent1 = Intent(this, PHQ9_2and3yearActivity::class.java)
                startActivity((intent1))
            }


        })

    }


    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}