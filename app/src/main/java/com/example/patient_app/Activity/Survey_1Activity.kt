package com.example.patient_app.Activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.patient_app.R
import com.example.patient_app.SFTP.File
import com.example.patient_app.SFTP.FileType
import kotlinx.android.synthetic.main.activity_survey1.*
import java.io.PrintWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey1)

        sharedPreferences = getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
        birth.updateDate(1990,1,1)

        val editor = sharedPreferences.edit()

        next_btn.setOnClickListener {

            editor.putBoolean("isToastShown", false)
            editor.apply()

            if (birth.dayOfMonth <= 9) {
                BasicInfo.day = "0" + (birth.dayOfMonth).toString()
            } else {
                BasicInfo.day = (birth.dayOfMonth).toString()
            }

            if (birth.month <= 8) {
                BasicInfo.month = "0" + (birth.month + 1).toString()
            } else {
                BasicInfo.month = (birth.month + 1).toString()
            }

            BasicInfo.year = birth.year

            if (gender_male.isChecked) {
                BasicInfo.gender = "male"
            } else if (gender_female.isChecked) {
                BasicInfo.gender = "female"
            } else {
                makeToast("성별을 선택하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.height = height.text.toString()
            if (BasicInfo.height.length == 0) {
                makeToast("키를 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.weight = weight.text.toString()
            if (BasicInfo.weight.length == 0) {
                makeToast("몸무게를 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (alcohol_no.isChecked) {
                BasicInfo.alcohol = "X"
            } else if (alcohol_yes.isChecked) {
                BasicInfo.alcohol = "O"
            } else {
                makeToast("음주 상태를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.alcohol_num = alcohol_num.text.toString()
            if (BasicInfo.alcohol_num.length == 0 && alcohol_yes.isChecked) {
                makeToast("음주 횟수를 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (alcohol_treat_no.isChecked) {
                BasicInfo.alcohol_treat = "X"
            } else if (alcohol_treat_yes.isChecked) {
                BasicInfo.alcohol_treat = "O"
            } else {
                makeToast("음주 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }


            if (smoking_no.isChecked) {
                BasicInfo.smoking = "X"
            } else if (smoking_yes.isChecked) {
                BasicInfo.smoking = "O"
            } else {
                makeToast("흡연 상태를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.smoking_num = smoking_num.text.toString()
            if (BasicInfo.smoking_num.length == 0 && smoking_yes.isChecked) {
                makeToast("흡연 횟수를 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (smoking_treat_no.isChecked) {
                BasicInfo.smoking_treat = "X"
            } else if (smoking_treat_yes.isChecked) {
                BasicInfo.smoking_treat = "O"
            } else {
                makeToast("흡연 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }


            if (exercise_no.isChecked) {
                BasicInfo.exercise = "X"
            } else if (exercise_yes.isChecked) {
                BasicInfo.exercise = "O"
            } else {
                makeToast("운동 상태를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.exercise_num = exercise_num.text.toString()
            if (BasicInfo.exercise_num.length == 0 && exercise_yes.isChecked) {
                makeToast("운동 횟수를 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (exercise_treat_no.isChecked) {
                BasicInfo.exercise_treat = "X"
            } else if (exercise_treat_yes.isChecked) {
                BasicInfo.exercise_treat = "O"
            } else {
                makeToast("운동 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (depressed_no.isChecked) {
                BasicInfo.depressed = "X"
            } else if (depressed_yes.isChecked) {
                BasicInfo.depressed = "O"
            } else {
                makeToast("우울 상태를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.depressed_num = depressed_num.text.toString()
            if (BasicInfo.depressed_num.length == 0 && depressed_yes.isChecked) {
                makeToast("우울 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (depressed_treat_no.isChecked) {
                BasicInfo.depressed_treat = "X"
            } else if (depressed_treat_yes.isChecked) {
                BasicInfo.depressed_treat = "O"
            } else {
                makeToast("우울 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }


            if (unrest_no.isChecked) {
                BasicInfo.unrest = "X"
            } else if (unrest_yes.isChecked) {
                BasicInfo.unrest = "O"
            } else {
                makeToast("불안 상태를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.unrest_num = unrest_num.text.toString()
            if (BasicInfo.unrest_num.length == 0 && unrest_yes.isChecked) {
                makeToast("불안 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (unrest_treat_no.isChecked) {
                BasicInfo.unrest_treat = "X"
            } else if (unrest_treat_yes.isChecked) {
                BasicInfo.unrest_treat = "O"
            } else {
                makeToast("불안 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (hypertension_no.isChecked) {
                BasicInfo.hypertension = "X"
            } else if (hypertension_yes.isChecked) {
                BasicInfo.hypertension = "O"
            } else {
                makeToast("고혈압 상태를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.hypertension_num = hypertension_num.text.toString()
            if (BasicInfo.hypertension_num.length == 0 && hypertension_yes.isChecked) {
                makeToast("고혈압 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (hypertension_treat_no.isChecked) {
                BasicInfo.hypertension_treat = "X"
            } else if (hypertension_treat_yes.isChecked) {
                BasicInfo.hypertension_treat = "O"
            } else {
                makeToast("고혈압 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }


            if (diabetes_no.isChecked) {
                BasicInfo.diabetes = "X"
            } else if (diabetes_yes.isChecked) {
                BasicInfo.diabetes = "O"
            } else {
                makeToast("당뇨 상태를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.diabetes_num = diabetes_num.text.toString()
            if (BasicInfo.diabetes_num.length == 0 && diabetes_yes.isChecked) {
                makeToast("당뇨 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (diabetes_treat_no.isChecked) {
                BasicInfo.diabetes_treat = "X"
            } else if (diabetes_treat_yes.isChecked) {
                BasicInfo.diabetes_treat = "O"
            } else {
                makeToast("당뇨 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }


            if (mental_no.isChecked) {
                BasicInfo.mental = "X"
            } else if (mental_yes.isChecked) {
                BasicInfo.mental = "O"
            } else {
                makeToast("정신질환 상태를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            BasicInfo.mental_num = mental_num.text.toString()
            if (BasicInfo.mental_num.length == 0 && mental_yes.isChecked) {
                makeToast("정신질환 지속 기간을 입력하세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (mental_treat_no.isChecked) {
                BasicInfo.mental_treat = "X"
            } else if (mental_treat_yes.isChecked) {
                BasicInfo.mental_treat = "O"
            } else {
                makeToast("정신질환 치료 여부를 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (Middle.isChecked) {
                BasicInfo.Education = "중졸"
            } else if (High.isChecked) {
                BasicInfo.Education = "고졸"
            } else if (Bachelor.isChecked) {
                BasicInfo.Education = "학사졸"
            } else if (Master.isChecked) {
                BasicInfo.Education = "석사졸"
            } else if (PhD.isChecked) {
                BasicInfo.Education = "박사졸"
            } else {
                makeToast("최종 학력을 선택해주세요.")
                editor.putBoolean("isToastShown", true)
                editor.apply()
            }

            if (!sharedPreferences.getBoolean("isToastShown", false)) {
                val onlyDate: String? =
                    LocalDate.now().format(DateTimeFormatter.ofPattern("MMdd"))
                val builder = AlertDialog.Builder(this)
                val id = "2" + BasicInfo.gender[0] + BasicInfo.year.toString().substring(2,4) + BasicInfo.month + BasicInfo.day + "$onlyDate"

                var file = java.io.File("/data/data/com.example.patient_app/files", "ID")
                if(!file.exists())
                    file.mkdir()

                file = java.io.File("/data/data/com.example.patient_app/files/ID", "id.txt")
                if(file.exists())
                    file.delete()

                val printWriter = PrintWriter(file)
                printWriter.println(id)
                printWriter.println("0")
                printWriter.println(LocalDate.now().year - BasicInfo.year + 1)
                if (gender_male.isChecked) {
                    printWriter.println(0)
                } else if (gender_female.isChecked) {
                    printWriter.println(1)
                }
                val bmi = (BasicInfo.weight.toDouble() / (BasicInfo.height.toDouble() * BasicInfo.height.toDouble())) * 10000
                printWriter.println(bmi)
                if (Middle.isChecked) {
                    printWriter.println(1)
                } else if (High.isChecked) {
                    printWriter.println(2)
                } else if (Bachelor.isChecked) {
                    printWriter.println(3)
                } else if (Master.isChecked) {
                    printWriter.println(4)
                } else if (PhD.isChecked) {
                    printWriter.println(5)
                }
                if(BasicInfo.alcohol_num == "")
                    printWriter.println(0)
                else
                    printWriter.println(BasicInfo.alcohol_num)
                printWriter.close()

                builder.setTitle("아이디 생성")
                    .setMessage("당신의 아이디는\n $id \n 입니다.")
                    .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                        MainActivity_HR.Patient_ID = id
                        finish()
                    }).show()
            }
        }

//        next_btn.setOnClickListener({
//
////            if (two.isChecked){
////                var intent1 = Intent(this, YMRS_2yearActivity::class.java)
////                startActivity((intent1))
////                if(MainActivity_HR.isFinished)
////                    finish()
////            }
////            else if (three.isChecked){
////                var intent2 = Intent(this, Whoqol_3yearActivity::class.java)
////                startActivity((intent2))
////                if(MainActivity_HR.isFinished)
////                    finish()
////            }
////            else{
////                makeToast("치료 기간을 선택해주세요.")
////            }
//
//
//        })

    }

    override fun onStop() {
        super.onStop()
        toast?.cancel()
    }
}
