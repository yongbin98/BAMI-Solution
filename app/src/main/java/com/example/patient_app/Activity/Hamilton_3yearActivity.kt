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
import kotlinx.android.synthetic.main.activity_hamilton3year.*
import kotlinx.android.synthetic.main.activity_hamilton3year.*

class Hamilton_3yearActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_hamilton3year)

        var hamilton_score1=0
        var hamilton_score2=0
        var hamilton_score3=0
        var hamilton_score4=0
        var hamilton_score5=0

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        val activitylauncher = openActivityResultLauncher()

        hamiltonNext_btn.setOnClickListener {

            editor.putBoolean("isToastShown", false)
            editor.apply()

            for (i in 1..21) {
                val one = resources.getIdentifier("Hamilton$i" + "_1", "id", packageName)
                val two = resources.getIdentifier("Hamilton$i" + "_2", "id", packageName)
                val three = resources.getIdentifier("Hamilton$i" + "_3", "id", packageName)
                val four = resources.getIdentifier("Hamilton$i" + "_4", "id", packageName)
                val five = resources.getIdentifier("Hamilton$i" + "_5", "id", packageName)

                val radioButtonOne = findViewById<RadioButton>(one)
                val radioButtonTwo = findViewById<RadioButton>(two)
                val radioButtonThree = findViewById<RadioButton>(three)
                val radioButtonFour = findViewById<RadioButton>(four)
                val radioButtonFive = findViewById<RadioButton>(five)

                if (radioButtonOne.isChecked) {
                    hamilton_score1++
                    Hamilton.hamilton[i - 1] = "1"
                } else if (radioButtonTwo.isChecked) {
                    hamilton_score2 = hamilton_score2 + 2
                    Hamilton.hamilton[i - 1] = "2"
                } else if (radioButtonThree.isChecked) {
                    hamilton_score3 = hamilton_score3 + 3
                    Hamilton.hamilton[i - 1] = "3"
                } else if (radioButtonFour.isChecked) {
                    hamilton_score4 = hamilton_score4 + 4
                    Hamilton.hamilton[i - 1] = "4"
                } else if (radioButtonFive.isChecked) {
                    hamilton_score5 = hamilton_score5 + 5
                    Hamilton.hamilton[i - 1] = "5"
                } else {
                    makeToast("$i 번에 응답해 주세요.")
                    editor.putBoolean("isToastShown", true)
                    editor.apply()
                }
            }

            if (!sharedPreferences.getBoolean("isToastShown", false)) {
                if ((MainActivity_HR.timeDiff.rem(28) == 0L || (MainActivity_HR.timeDiff.div(28) > MainActivity_HR.treatFinish.div(28))) && MainActivity_HR.treatYear == "3") {
                    val intent = Intent(this, MARS_3yearActivity::class.java)
                    activitylauncher.launch(intent)
                } else {
                    val intent = Intent(this, Thankyou::class.java)
                    activitylauncher.launch(intent)
                }
            }

        }
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