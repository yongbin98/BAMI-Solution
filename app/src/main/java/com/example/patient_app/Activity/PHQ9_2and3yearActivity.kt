package com.example.patient_app.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_phq92and3year.*
import kotlinx.android.synthetic.main.activity_ymrs2year.*

class PHQ9_2and3yearActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_phq92and3year)

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val activitylauncher = openActivityResultLauncher()

        PHQNext_btn.setOnClickListener{

            editor.putBoolean("isToastShown", false)
            editor.apply()

            for (i in 1..9) {
                val one = resources.getIdentifier("PHQ$i" + "_1", "id", packageName)
                val two = resources.getIdentifier("PHQ$i" + "_2", "id", packageName)
                val three = resources.getIdentifier("PHQ$i" + "_3", "id", packageName)
                val four = resources.getIdentifier("PHQ$i" + "_4", "id", packageName)

                val radioButtonOne = findViewById<RadioButton>(one)
                val radioButtonTwo = findViewById<RadioButton>(two)
                val radioButtonThree = findViewById<RadioButton>(three)
                val radioButtonFour = findViewById<RadioButton>(four)

                if (radioButtonOne.isChecked) {
                    PHQanswer.PHQ[i - 1] = "1"
                } else if (radioButtonTwo.isChecked) {
                    PHQanswer.PHQ[i - 1] = "2"
                } else if (radioButtonThree.isChecked) {
                    PHQanswer.PHQ[i - 1] = "3"
                } else if (radioButtonFour.isChecked) {
                    PHQanswer.PHQ[i - 1] = "4"
                } else {
                    makeToast("$i 번에 응답해 주세요.")
                    editor.putBoolean("isToastShown", true)
                    editor.apply()
                }
            }

            if (!sharedPreferences.getBoolean("isToastShown", false)) {
                if ((MainActivity_HR.timeDiff.rem(14) == 0L ||  (MainActivity_HR.timeDiff.div(14) > MainActivity_HR.treatFinish.div(14))) ) {
                    val intent = Intent(this, YMRS_2yearActivity::class.java)
                    activitylauncher.launch(intent)
                }
//                else if ((MainActivity_HR.timeDiff.rem(28) == 0L ||  (MainActivity_HR.timeDiff.div(28) > MainActivity_HR.treatFinish.div(28)))&& MainActivity_HR.treatYear == "3") {
//                    val intent = Intent(this, SSI_3yearActivity::class.java)
//                    activitylauncher.launch(intent) }
                else {
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