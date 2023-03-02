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
import kotlinx.android.synthetic.main.activity_phq92and3year.*
import kotlinx.android.synthetic.main.activity_ymrs2year.*

class PHQ9_2and3yearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phq92and3year)



        var option = resources.getStringArray(R.array.PHQ)
        var adapter = ArrayAdapter<String>(this, R.layout.spinner_item_layout,option)

        PHQ1.adapter = adapter
        PHQ2.adapter = adapter
        PHQ3.adapter = adapter
        PHQ4.adapter = adapter
        PHQ5.adapter = adapter
        PHQ6.adapter = adapter
        PHQ7.adapter = adapter
        PHQ8.adapter = adapter
        PHQ9.adapter = adapter


        PHQ1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ1_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        PHQ2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ2_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        PHQ3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ3_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        PHQ4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ4_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        PHQ5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ5_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        PHQ6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ6_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        PHQ7.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ7_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        PHQ8.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ8_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        PHQ9.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                PHQanswer.PHQ9_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val activitylauncher = openActivityResultLauncher()


        PHQNext_btn.setOnClickListener({

            if ((PHQanswer.PHQ1_ans == 0) || (PHQanswer.PHQ2_ans ==0) || (PHQanswer.PHQ3_ans ==0) || (PHQanswer.PHQ4_ans ==0) || (PHQanswer.PHQ5_ans ==0) || (PHQanswer.PHQ6_ans ==0) || (PHQanswer.PHQ7_ans ==0) || (PHQanswer.PHQ8_ans ==0) || (PHQanswer.PHQ9_ans ==0))
            {
                Toast.makeText(this, "모든 항목에 응답해주세요.",Toast.LENGTH_SHORT).show()
            }
            else{
                if (MainActivity_HR.timeDiff.rem(7) == 0L && MainActivity_HR.treatYear == "2") {
                    val intent = Intent(this, YMRS_2yearActivity::class.java)
                    activitylauncher.launch(intent)
                } else if (MainActivity_HR.timeDiff.rem(30) == 0L && MainActivity_HR.treatYear == "3") {
                    val intent = Intent(this, SSI_3yearActivity::class.java)
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