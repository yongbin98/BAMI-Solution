package com.example.patient_app.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_survey1.*

class Survey_1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey1)

        save_btn.setOnClickListener({

            val day: Int = birth.dayOfMonth
            val month: Int = birth.month
            val year: Int = birth.year
            val gender: String

            if (gender_male.isChecked){
                gender = "male"
            }
            else if(gender_female.isChecked){
                gender = "female"
            }
            else{
                Toast.makeText(this, "성별을 선택하세요.", Toast.LENGTH_SHORT).show()
            }

            var height = height.text.toString()
            if (height.length == 0){
                Toast.makeText(this, "키를 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            var weight = weight.text.toString()
            if (weight.length == 0){
                Toast.makeText(this, "몸무게를 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (alcohol_no.isChecked){
                val alcohol = "음주 안함"
            }
            else if(alcohol_yes.isChecked){
                val alcohol = "음주 중"
            }
            else{
                Toast.makeText(this, "음주 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            var alcohol_num = alcohol_num.text.toString()
            if (alcohol_num.length == 0){
                Toast.makeText(this, "음주 횟수를 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (alcohol_treat_no.isChecked){
                val alcohol_treat = "음주 치료 하지 않음"
            }
            else if(alcohol_treat_yes.isChecked){
                val alcohol = "음주 치료 함"
            }
            else{
                Toast.makeText(this, "음주 치료 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }


            if (smoking_no.isChecked){
                val smoking = "흡연 안함"
            }
            else if(smoking_yes.isChecked){
                val smoking = "흡연 중"
            }
            else{
                Toast.makeText(this, "흡연 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            var smoking_num = smoking_num.text.toString()
            if (smoking_num.length == 0){
                Toast.makeText(this, "흡연 횟수를 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (smoking_treat_no.isChecked){
                val smoking_treat = "흡연 치료 하지 않음"
            }
            else if(smoking_treat_yes.isChecked){
                val smoking_treat = "흡연 치료 함"
            }
            else{
                Toast.makeText(this, "흡연 치료 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }


            if (exercise_no.isChecked){
                val smoking = "운동 안함"
            }
            else if(exercise_yes.isChecked){
                val smoking = "운동 중"
            }
            else{
                Toast.makeText(this, "운동 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            var exercise_num = exercise_num.text.toString()
            if (exercise_num.length == 0){
                Toast.makeText(this, "운동 횟수를 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (exercise_treat_no.isChecked){
                val exercise_treat = "운동 치료 하지 않음"
            }
            else if(exercise_treat_yes.isChecked){
                val exercise_treat = "운동 치료 함"
            }
            else{
                Toast.makeText(this, "운동 치료 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            if (depressed_no.isChecked){
                val depressed = "우울 안함"
            }
            else if(exercise_yes.isChecked){
                val depressed = "우울함"
            }
            else{
                Toast.makeText(this, "우울 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            var depressed_num = depressed_num.text.toString()
            if (depressed_num.length == 0){
                Toast.makeText(this, "우울 지속 기간을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (depressed_treat_no.isChecked){
                val depressed_treat = "우울 치료 하지 않음"
            }
            else if(depressed_treat_yes.isChecked){
                val depressed_treat = "우울 치료 함"
            }
            else{
                Toast.makeText(this, "우울 치료 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }


            if (unrest_no.isChecked){
                val depressed = "불안 안함"
            }
            else if(unrest_yes.isChecked){
                val depressed = "불안함"
            }
            else{
                Toast.makeText(this, "불안 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            var unrest_num = unrest_num.text.toString()
            if (unrest_num.length == 0){
                Toast.makeText(this, "불안 지속 기간을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (unrest_treat_no.isChecked){
                val unrest_treat = "불안 치료 하지 않음"
            }
            else if(unrest_treat_yes.isChecked){
                val unrest_treat = "불안 치료 함"
            }
            else{
                Toast.makeText(this, "불안 치료 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            if (hypertension_no.isChecked){
                val depressed = "고혈압 없음"
            }
            else if(hypertension_yes.isChecked){
                val depressed = "고혈압 있음"
            }
            else{
                Toast.makeText(this, "고혈압 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            var hypertension_num = hypertension_num.text.toString()
            if (hypertension_num.length == 0){
                Toast.makeText(this, "고혈압 지속 기간을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (hypertension_treat_no.isChecked){
                val hypertension_treat = "고혈압 치료 하지 않음"
            }
            else if(hypertension_treat_yes.isChecked){
                val unrest_treat = "고혈압 치료 함"
            }
            else{
                Toast.makeText(this, "고혈압 치료 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }


            if (diabetes_no.isChecked){
                val diabetes = "당뇨 없음"
            }
            else if(diabetes_yes.isChecked){
                val diabetes = "당뇨 있음"
            }
            else{
                Toast.makeText(this, "당뇨 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            var diabetes_num = diabetes_num.text.toString()
            if (diabetes_num.length == 0){
                Toast.makeText(this, "당뇨 지속 기간을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (diabetes_treat_no.isChecked){
                val diabetes_treat = "당뇨 치료 하지 않음"
            }
            else if(diabetes_treat_yes.isChecked){
                val diabetes_treat = "당뇨 치료 함"
            }
            else{
                Toast.makeText(this, "당뇨 치료 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }


            if (mental_no.isChecked){
                val mental = "정신질환 없음"
            }
            else if(mental_yes.isChecked){
                val mental = "정신질환 있음"
            }
            else{
                Toast.makeText(this, "정신질환 상태를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            var mental_num = mental_num.text.toString()
            if (mental_num.length == 0){
                Toast.makeText(this, "정신질환 지속 기간을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            if (mental_treat_no.isChecked){
                val mental_treat = "정신질환 치료 하지 않음"
            }
            else if(mental_treat_yes.isChecked){
                val mental_treat = "정신질환 치료 함"
            }
            else{
                Toast.makeText(this, "정신질환 치료 여부를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(this, "저장완료", Toast.LENGTH_SHORT).show()

        })

        next_btn.setOnClickListener({
            if (two.isChecked){
                val intent1 = Intent(this, Survey_2yearActivity::class.java)
                startActivity((intent1))
            }
            else if (three.isChecked){
                val intent2 = Intent(this, Survey_3yearActivity::class.java)
                startActivity((intent2))
            }
            else{
                Toast.makeText(this, "치료 기간을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        })



        }





    }
