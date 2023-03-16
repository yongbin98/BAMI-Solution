package com.example.patient_app.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.TextViewCompat
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_gad72year.*
import kotlinx.android.synthetic.main.activity_ymrs2year.*

class GAD7_2yearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gad72year)

        var option2 = resources.getStringArray(R.array.GAD)
        var adapter2 = ArrayAdapter<String>(this, R.layout.spinner_item_layout,option2)
        val activitylauncher = openActivityResultLauncher()

        GAD1.adapter = adapter2
        GAD2.adapter = adapter2
        GAD3.adapter = adapter2
        GAD4.adapter = adapter2
        GAD5.adapter = adapter2
        GAD6.adapter = adapter2
        GAD7.adapter = adapter2

        GAD1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GADanswer.GAD1_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        GAD2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GADanswer.GAD2_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        GAD3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GADanswer.GAD3_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        GAD4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GADanswer.GAD4_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        GAD5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GADanswer.GAD5_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        GAD6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GADanswer.GAD6_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        GAD7.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GADanswer.GAD7_ans = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        GADNext_btn.setOnClickListener({

            if ((GADanswer.GAD1_ans == 0) || (GADanswer.GAD2_ans ==0) || (GADanswer.GAD3_ans ==0) || (GADanswer.GAD4_ans ==0) || (GADanswer.GAD5_ans ==0) || (GADanswer.GAD6_ans ==0) || (GADanswer.GAD7_ans ==0))
            {
                Toast.makeText(this, "모든 항목에 응답해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                if (MainActivity_HR.timeDiff.rem(14) == 0L) {
                    val intent = Intent(this, PHQ9_2and3yearActivity::class.java)
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