package com.example.patient_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_gad72year.*
import kotlinx.android.synthetic.main.activity_ymrs2year.*

class GAD7_2yearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gad72year)

        var option2 = resources.getStringArray(R.array.GAD)
        var adapter2 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,option2)

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
            var intent1 = Intent(this, insomnia_2yearActivity::class.java)
            startActivity((intent1))
        })










    }
}