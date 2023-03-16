package com.example.patient_app.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mars3year)

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        val activitylauncher = openActivityResultLauncher()

        val Nolist = mutableListOf(1,2,3,4,5,6,9,10)
        val Yeslist = mutableListOf(7,8)
        var MARSscore = 0


        marsNext_btn.setOnClickListener({

            editor.putBoolean("isToastShown",false)
            editor.apply()

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
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
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
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }
            Mars.Score = "$MARSscore 점"

            if(!sharedPreferences.getBoolean("isToastShown",false)){
                if (MainActivity_HR.timeDiff.rem(28) == 0L && MainActivity_HR.treatYear == "3") {
                    val intent = Intent(this, Whoqol_3yearActivity::class.java)
                    activitylauncher.launch(intent)
                } else {
                    val intent = Intent(this, Thankyou::class.java)
                    activitylauncher.launch(intent)
                }
            }

        })
    }

    private fun openActivityResultLauncher(): ActivityResultLauncher<Intent> {
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        return resultLauncher
    }


    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}