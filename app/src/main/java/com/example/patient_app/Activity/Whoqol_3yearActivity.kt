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

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whoqol3year)

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        val activitylauncher = openActivityResultLauncher()

        whoqolNext_btn.setOnClickListener({


            editor.putBoolean("isToastShown",false)
            editor.apply()

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
                    WHOQ.WHOQ[i-1] = "1"
                }
                else if (radioButtonTwo.isChecked){
                    WHOQ.WHOQ[i-1] = "2"
                }
                else if (radioButtonThree.isChecked){
                    WHOQ.WHOQ[i-1] = "3"
                }
                else if (radioButtonFour.isChecked){
                    WHOQ.WHOQ[i-1] = "4"
                }
                else if (radioButtonFive.isChecked){
                    WHOQ.WHOQ[i-1] = "5"
                }
                else{
                    makeToast("$i 번에 응답해 주세요.")
                    editor.putBoolean("isToastShown",true)
                    editor.apply()
                }
            }

            if(!sharedPreferences.getBoolean("isToastShown",false)){
                val intent = Intent(this, Thankyou::class.java)
                activitylauncher.launch(intent)
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
}