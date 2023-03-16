package com.example.patient_app.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_gad72year.*
import kotlinx.android.synthetic.main.activity_ssi3year.*

class SSI_3yearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ssi3year)

        var Soption1 = resources.getStringArray(R.array.SSI1)
        var Sadapter1 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption1)
        var Soption2 = resources.getStringArray(R.array.SSI2)
        var Sadapter2 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption2)
        var Soption3 = resources.getStringArray(R.array.SSI3)
        var Sadapter3 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption3)
        var Soption4 = resources.getStringArray(R.array.SSI4)
        var Sadapter4 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption4)
        var Soption5 = resources.getStringArray(R.array.SSI5)
        var Sadapter5 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption5)
        var Soption6 = resources.getStringArray(R.array.SSI6)
        var Sadapter6 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption6)
        var Soption7 = resources.getStringArray(R.array.SSI7)
        var Sadapter7 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption7)
        var Soption8 = resources.getStringArray(R.array.SSI8)
        var Sadapter8 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption8)
        var Soption9 = resources.getStringArray(R.array.SSI9)
        var Sadapter9 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption9)
        var Soption10 = resources.getStringArray(R.array.SSI10)
        var Sadapter10 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption10)
        var Soption11 = resources.getStringArray(R.array.SSI11)
        var Sadapter11 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption11)
        var Soption12 = resources.getStringArray(R.array.SSI12)
        var Sadapter12 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption12)
        var Soption13 = resources.getStringArray(R.array.SSI13)
        var Sadapter13 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption13)
        var Soption14 = resources.getStringArray(R.array.SSI14)
        var Sadapter14 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption14)
        var Soption15 = resources.getStringArray(R.array.SSI15)
        var Sadapter15 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption15)
        var Soption16 = resources.getStringArray(R.array.SSI16)
        var Sadapter16 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption16)
        var Soption17 = resources.getStringArray(R.array.SSI17)
        var Sadapter17 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption17)
        var Soption18 = resources.getStringArray(R.array.SSI18)
        var Sadapter18 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption18)
        var Soption19 = resources.getStringArray(R.array.SSI19)
        var Sadapter19 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,Soption19)

        SSI1.adapter = Sadapter1
        SSI2.adapter = Sadapter2
        SSI3.adapter = Sadapter3
        SSI4.adapter = Sadapter4
        SSI5.adapter = Sadapter5
        SSI6.adapter = Sadapter6
        SSI7.adapter = Sadapter7
        SSI8.adapter = Sadapter8
        SSI9.adapter = Sadapter9
        SSI10.adapter = Sadapter10
        SSI11.adapter = Sadapter11
        SSI12.adapter = Sadapter12
        SSI13.adapter = Sadapter13
        SSI14.adapter = Sadapter14
        SSI15.adapter = Sadapter15
        SSI16.adapter = Sadapter16
        SSI17.adapter = Sadapter17
        SSI18.adapter = Sadapter18
        SSI19.adapter = Sadapter19


        SSI1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI1_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI2_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI3_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI4_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI5_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI6_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI7.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI7_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI8.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI8_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI9.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI9_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI10.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI10_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI11.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI11_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI12.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI12_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI13.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI13_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI14.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI14_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI15.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI15_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI16.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI16_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI17.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI17_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI18.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI18_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        SSI19.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SSIanswer.SSI19_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val activitylauncher = openActivityResultLauncher()

        SSINext_btn.setOnClickListener({

            if ((SSIanswer.SSI1_ans == 0) || (SSIanswer.SSI2_ans ==0) || (SSIanswer.SSI3_ans ==0) || (SSIanswer.SSI4_ans ==0) || (SSIanswer.SSI5_ans ==0) || (SSIanswer.SSI6_ans ==0) || (SSIanswer.SSI7_ans ==0)|| (SSIanswer.SSI8_ans ==0)|| (SSIanswer.SSI9_ans ==0)|| (SSIanswer.SSI10_ans ==0)|| (SSIanswer.SSI11_ans ==0)|| (SSIanswer.SSI12_ans ==0)|| (SSIanswer.SSI13_ans ==0)|| (SSIanswer.SSI14_ans ==0)|| (SSIanswer.SSI15_ans ==0)|| (SSIanswer.SSI16_ans ==0)|| (SSIanswer.SSI17_ans ==0)|| (SSIanswer.SSI18_ans ==0)|| (SSIanswer.SSI19_ans ==0))
            {
                Toast.makeText(this, "모든 항목에 응답해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                if (MainActivity_HR.timeDiff.rem(28) == 0L && MainActivity_HR.treatYear == "3") {
                    val intent = Intent(this, Hamilton_3yearActivity::class.java)
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

}