package com.example.patient_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_phq92and3year.*
import kotlinx.android.synthetic.main.activity_ymrs2year.*

class PHQ9_2and3yearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phq92and3year)



        var option = resources.getStringArray(R.array.PHQ)
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,option)

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


        PHQNext_btn.setOnClickListener({
            var intent1 = Intent(this, GAD7_2yearActivity::class.java)
            startActivity((intent1))
        })




    }
}