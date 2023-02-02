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
        setContentView(R.layout.activity_insomnia2year)


        var Insomnia_score1=0
        var Insomnia_score2=0
        var Insomnia_score3=0
        var Insomnia_score4=0
        var Insomnia_score5=0

        insomniaSave_btn.setOnClickListener({

            for (i in 1..3) {
                val one1 = resources.getIdentifier("Insomnia1_$i" + "_1", "id", packageName)
                val two1 = resources.getIdentifier("Insomnia1_$i" + "_2", "id", packageName)
                val three1 = resources.getIdentifier("Insomnia1_$i" + "_3", "id", packageName)
                val four1 = resources.getIdentifier("Insomnia1_$i"+"_4","id", packageName)
                val five1 = resources.getIdentifier("Insomnia1_$i"+"_5","id", packageName)

                val radioButtonOne1 = findViewById<RadioButton>(one1)
                val radioButtonTwo1 = findViewById<RadioButton>(two1)
                val radioButtonThree1 = findViewById<RadioButton>(three1)
                val radioButtonFour1 = findViewById<RadioButton>(four1)
                val radioButtonFive1 = findViewById<RadioButton>(five1)

            }


            for (i in 2..5) {
                val one = resources.getIdentifier("Insomnia$i"+"_1","id", packageName)
                val two = resources.getIdentifier("Insomnia$i"+"_2","id", packageName)
                val three = resources.getIdentifier("Insomnia$i"+"_3","id", packageName)
                val four = resources.getIdentifier("Insomnia$i"+"_4","id", packageName)
                val five = resources.getIdentifier("Insomnia$i"+"_5","id", packageName)

                val radioButtonOne = findViewById<RadioButton>(one)
                val radioButtonTwo = findViewById<RadioButton>(two)
                val radioButtonThree = findViewById<RadioButton>(three)
                val radioButtonFour = findViewById<RadioButton>(four)
                val radioButtonFive = findViewById<RadioButton>(five)

                if (radioButtonOne.isChecked){
                    Insomnia_score1++
                }
                else if (radioButtonTwo.isChecked){
                    Insomnia_score2 = Insomnia_score2+2
                }
                else if (radioButtonThree.isChecked){
                    Insomnia_score3 = Insomnia_score3+3
                }
                else if (radioButtonFour.isChecked){
                    Insomnia_score4 = Insomnia_score4+4
                }
                else if (radioButtonFive.isChecked){
                    Insomnia_score5 = Insomnia_score5+5
                }
                else{
                    makeToast("$i 번에 응답해 주세요.")
                }
            }

            Toast.makeText(this,"저장 완료",Toast.LENGTH_SHORT).show()



        })

        insomniaNext_btn.setOnClickListener({
            var intent1 = Intent(this, Stress_2and3yearActivity::class.java)
            startActivity((intent1))
        })





    }
}