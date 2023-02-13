package com.example.patient_app.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_survey1.*

class Survey_1Activity : AppCompatActivity() {
    private var toast: Toast? = null
    private fun makeToast(message: String){
        try{
            toast?.cancel()
            toast = Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT)
            toast?.show()
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey1)

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        next_btn.setOnClickListener({

            editor.putBoolean("isToastShown",false)
            editor.apply()

            BasicInfo.day =birth.dayOfMonth
            BasicInfo.month = birth.month
            BasicInfo.year = birth.year

            if (gender_male.isChecked){
                BasicInfo.gender = "남"
            }
            else if(gender_female.isChecked){
                BasicInfo.gender = "여"
            }
            else{
                makeToast("성별을 선택하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.height = height.text.toString()
            if (BasicInfo.height.length == 0){
                makeToast("키를 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.weight = weight.text.toString()
            if (BasicInfo.weight.length == 0){
                makeToast("몸무게를 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (alcohol_no.isChecked){
                BasicInfo.alcohol = "X"
            }
            else if(alcohol_yes.isChecked){
                BasicInfo.alcohol = "O"
            }
            else{
                makeToast("음주 상태를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.alcohol_num = alcohol_num.text.toString()
            if (BasicInfo.alcohol_num.length == 0){
                makeToast("음주 횟수를 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (alcohol_treat_no.isChecked){
                BasicInfo.alcohol_treat = "X"
            }
            else if(alcohol_treat_yes.isChecked){
                BasicInfo.alcohol_treat = "O"
            }
            else{
                makeToast("음주 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }


            if (smoking_no.isChecked){
                BasicInfo.smoking = "X"
            }
            else if(smoking_yes.isChecked){
                BasicInfo.smoking = "O"
            }
            else{
                makeToast("흡연 상태를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.smoking_num = smoking_num.text.toString()
            if (BasicInfo.smoking_num.length == 0){
                makeToast("흡연 횟수를 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (smoking_treat_no.isChecked){
                BasicInfo.smoking_treat = "X"
            }
            else if(smoking_treat_yes.isChecked){
                BasicInfo.smoking_treat = "O"
            }
            else{
                makeToast("흡연 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }


            if (exercise_no.isChecked){
                BasicInfo.smoking = "X"
            }
            else if(exercise_yes.isChecked){
                BasicInfo.smoking = "O"
            }
            else{
                makeToast("운동 상태를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.exercise_num = exercise_num.text.toString()
            if (BasicInfo.exercise_num.length == 0){
                makeToast("운동 횟수를 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (exercise_treat_no.isChecked){
                BasicInfo.exercise_treat = "X"
            }
            else if(exercise_treat_yes.isChecked){
                BasicInfo.exercise_treat = "O"
            }
            else{
                makeToast("운동 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (depressed_no.isChecked){
                BasicInfo.depressed = "X"
            }
            else if(exercise_yes.isChecked){
                BasicInfo.depressed = "O"
            }
            else{
                makeToast("우울 상태를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.depressed_num = depressed_num.text.toString()
            if (BasicInfo.depressed_num.length == 0){
                makeToast("우울 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (depressed_treat_no.isChecked){
                BasicInfo.depressed_treat = "X"
            }
            else if(depressed_treat_yes.isChecked){
                BasicInfo.depressed_treat = "O"
            }
            else{
                makeToast("우울 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }


            if (unrest_no.isChecked){
                BasicInfo.unrest = "X"
            }
            else if(unrest_yes.isChecked){
                BasicInfo.unrest = "O"
            }
            else{
                makeToast("불안 상태를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.unrest_num = unrest_num.text.toString()
            if (BasicInfo.unrest_num.length == 0){
                makeToast("불안 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (unrest_treat_no.isChecked){
                BasicInfo.unrest_treat = "X"
            }
            else if(unrest_treat_yes.isChecked){
                BasicInfo.unrest_treat = "O"
            }
            else{
                makeToast("불안 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (hypertension_no.isChecked){
                BasicInfo.hypertension = "X"
            }
            else if(hypertension_yes.isChecked){
                BasicInfo.hypertension = "O"
            }
            else{
                makeToast("고혈압 상태를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.hypertension_num = hypertension_num.text.toString()
            if (BasicInfo.hypertension_num.length == 0){
                makeToast("고혈압 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (hypertension_treat_no.isChecked){
                BasicInfo.hypertension_treat = "X"
            }
            else if(hypertension_treat_yes.isChecked){
                BasicInfo.hypertension_treat = "O"
            }
            else{
                makeToast("고혈압 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }


            if (diabetes_no.isChecked){
                BasicInfo.diabetes = "X"
            }
            else if(diabetes_yes.isChecked){
                BasicInfo.diabetes = "O"
            }
            else{
                makeToast("당뇨 상태를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.diabetes_num = diabetes_num.text.toString()
            if (BasicInfo.diabetes_num.length == 0){
                makeToast("당뇨 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (diabetes_treat_no.isChecked){
                BasicInfo.diabetes_treat = "X"
            }
            else if(diabetes_treat_yes.isChecked){
                BasicInfo.diabetes_treat = "O"
            }
            else{
                makeToast("당뇨 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }


            if (mental_no.isChecked){
                BasicInfo.mental = "X"
            }
            else if(mental_yes.isChecked){
                BasicInfo.mental = "O"
            }
            else{
                makeToast("정신질환 상태를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            BasicInfo.mental_num = mental_num.text.toString()
            if (BasicInfo.mental_num.length == 0){
                makeToast("정신질환 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (mental_treat_no.isChecked){
                BasicInfo.mental_treat = "X"
            }
            else if(mental_treat_yes.isChecked){
                BasicInfo.mental_treat = "O"
            }
            else{
                makeToast("정신질환 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()
            }

            if (two.isChecked){
                BasicInfo.length = "2년차"
            }
            else if(three.isChecked){
                BasicInfo.length = "3년차"
            }
            else{
                makeToast("치료 기간을 선택해주세요.")
                editor.putBoolean("isToastShown",true)
                editor.apply()

            }

            if(!sharedPreferences.getBoolean("isToastShown",false)){
                    if (two.isChecked){
                        var intent1 = Intent(this, YMRS_2yearActivity::class.java)
                        startActivity((intent1))
                    }
                    else if (three.isChecked){
                        var intent2 = Intent(this, Whoqol_3yearActivity::class.java)
                        startActivity((intent2))
                    }
                }
        })

//        next_btn.setOnClickListener({
//
//            if (two.isChecked){
//                var intent1 = Intent(this, YMRS_2yearActivity::class.java)
//                startActivity((intent1))
//            }
//            else if (three.isChecked){
//                var intent2 = Intent(this, Whoqol_3yearActivity::class.java)
//                startActivity((intent2))
//            }
//            else{
//                makeToast("치료 기간을 선택해주세요.")
//            }
//
//        })

        }

    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
    }
