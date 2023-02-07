package com.example.patient_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_insomnia2year.*

class insomnia_2yearActivity : AppCompatActivity() {

    private var toast: Toast? = null
    private fun makeToast(message: String) {
        try {
            toast?.cancel()
            toast = Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
            toast?.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insomnia2year)

        insomniaSave_btn.setOnClickListener({

            for (i in 1..3) {
                val one1 = resources.getIdentifier("insomnia1_$i" + "_1", "id", packageName)
                val two1 = resources.getIdentifier("insomnia1_$i" + "_2", "id", packageName)
                val three1 = resources.getIdentifier("insomnia1_$i" + "_3", "id", packageName)
                val four1 = resources.getIdentifier("insomnia1_$i" + "_4", "id", packageName)
                val five1 = resources.getIdentifier("insomnia1_$i" + "_5", "id", packageName)

                val radioButtonOne1 = findViewById<RadioButton>(one1)
                val radioButtonTwo1 = findViewById<RadioButton>(two1)
                val radioButtonThree1 = findViewById<RadioButton>(three1)
                val radioButtonFour1 = findViewById<RadioButton>(four1)
                val radioButtonFive1 = findViewById<RadioButton>(five1)

                if (radioButtonOne1.isChecked) {
                    Insomnia.insomnia1[i - 1] = "1"
                } else if (radioButtonTwo1.isChecked) {
                    Insomnia.insomnia1[i - 1] = "2"
                } else if (radioButtonThree1.isChecked) {
                    Insomnia.insomnia1[i - 1] = "3"
                } else if (radioButtonFour1.isChecked) {
                    Insomnia.insomnia1[i - 1] = "4"
                } else if (radioButtonFive1.isChecked) {
                    Insomnia.insomnia1[i - 1] = "5"
                } else {
                    makeToast("1-$i 번에 응답해 주세요.")
                }
            }

            for (i in 2..5) {
                val one = resources.getIdentifier("insomnia$i" + "_1", "id", packageName)
                val two = resources.getIdentifier("insomnia$i" + "_2", "id", packageName)
                val three = resources.getIdentifier("insomnia$i" + "_3", "id", packageName)
                val four = resources.getIdentifier("insomnia$i" + "_4", "id", packageName)
                val five = resources.getIdentifier("insomnia$i" + "_5", "id", packageName)

                val radioButtonOne = findViewById<RadioButton>(one)
                val radioButtonTwo = findViewById<RadioButton>(two)
                val radioButtonThree = findViewById<RadioButton>(three)
                val radioButtonFour = findViewById<RadioButton>(four)
                val radioButtonFive = findViewById<RadioButton>(five)

                if (radioButtonOne.isChecked) {
                    Insomnia.insomnia2345[i-2] = "1"
                } else if (radioButtonTwo.isChecked) {
                    Insomnia.insomnia2345[i-2] = "2"
                } else if (radioButtonThree.isChecked) {
                    Insomnia.insomnia2345[i-2] = "3"
                } else if (radioButtonFour.isChecked) {
                    Insomnia.insomnia2345[i-2] = "4"
                } else if (radioButtonFive.isChecked) {
                    Insomnia.insomnia2345[i-2] = "5"
                } else {
                    makeToast("$i 번에 응답해 주세요.")
                }
            }

            Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()
        })


        insomniaNext_btn.setOnClickListener({
            var intent5 = Intent(this, Stress_2and3yearActivity::class.java)
            startActivity((intent5))
        })

    }
}





