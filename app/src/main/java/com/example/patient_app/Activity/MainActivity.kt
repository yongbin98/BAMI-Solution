package com.example.patient_app.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.patient_app.R
import com.example.patient_app.WeatherAPI.APIService
import com.example.patient_app.samsungHealth.HealthService
import com.example.patient_app.samsungHealth.PERMISSIONS
import com.example.patient_app.samsungHealth.REQUEST_ALL_PERMISSION
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_thankyou.*
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("MissingPermission")
class MainActivity : AppCompatActivity() {
    private val TAG = "Main"
    private val myCoroutinescope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val utilDate = Date()
        val formatType = SimpleDateFormat("yyyy년 MM월 dd일")
        date.text = formatType.format(utilDate)
        val activitylauncher = openActivityResultLauncher()

        btn_SamsungHealth.visibility = Button.VISIBLE
        btn_Survey.visibility = Button.INVISIBLE
        survey_end.visibility = TextView.INVISIBLE

        val surveyProgress: TextView = findViewById(R.id.survey_progress)

        calculateSurvey()
        surveyProgress.text = "연구 D+" + (MainActivity_HR.timeDiff.toInt() +1).toString()

        surveyProgress.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_NUMBER
            builder.setTitle("설문 일수 수정")
            builder.setMessage("설문을 시작한 날짜를 입력하세요. (예: 0912)")
            builder.setView(input)

            builder.setPositiveButton("확인") {dialog, which ->
                val inputDate = input.text.toString()

                if (inputDate.length == 4 && inputDate.matches(Regex("\\d{4}"))) {
                    val month = inputDate.substring(0,2).toIntOrNull()
                    val day = inputDate.substring(2,4).toIntOrNull()

                    if (month !=null && month in 1..12 && day != null && day in 1..31) {
                        MainActivity_HR.Patient_ID = MainActivity_HR.Patient_ID.substring(0,8) + inputDate
                        calculateSurvey()
                        surveyProgress.text = "연구 D+" + (MainActivity_HR.timeDiff.toInt() +1).toString()

                        updatePatientID()
                    } else{
                        Toast.makeText(this, "유효한 날짜를 입력하세요. (예: 0605)", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "4자리 숫자를 입력하세요. (예: 0824)", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("취소") { dialog, which ->
                dialog.dismiss()
            }

            builder.show()
        }



        btn_SamsungHealth.setOnClickListener {
            myCoroutinescope.launch {
                HealthService.connect(this@MainActivity) {
                    Log.i(TAG, "Get Permission!!")
                    true
                }
                Log.i(TAG, "START")
                HealthService.start(this@MainActivity)
                val (HR, StepCheck) = HealthService.getHealthData()
                Log.i(TAG, "HR : $HR , Stepcount : $StepCheck")
                APIService.connect(this@MainActivity) {
                    myCoroutinescope.launch {
                        weatherScreen(it)
                        rate.text = HR.toInt().toString()
                        step.text = StepCheck.toString()
                        MainActivity_HR.HR = HR.toInt().toString()
                        MainActivity_HR.Steps = StepCheck.toString()
                        btn_SamsungHealth.visibility = Button.GONE
                        if(MainActivity_HR.treatFinish - MainActivity_HR.timeDiff >= 1)
                        {
                            btn_SamsungHealth.visibility = Button.INVISIBLE
                            survey_end.visibility = TextView.VISIBLE
                            btn_Survey.visibility = Button.INVISIBLE
                        }
                        else{
                            btn_SamsungHealth.visibility = Button.INVISIBLE
                            btn_Survey.visibility = Button.VISIBLE
                            survey_end.visibility = TextView.INVISIBLE
                        }

                        var file = java.io.File("/data/data/com.example.patient_app/files/ID", "stressOutput.txt")
                        if(file.exists()) {
                            MainActivity_HR.outputStress = FileReader(file).readLines().iterator().next().toInt()
                        }
                        if(MainActivity_HR.outputStress == 0){
                            titleStress.text = "스트레스 지수 (1 ~ 5) : NaN"
                        }
                        else{
                            titleStress.text = "스트레스 지수 (1 ~ 5) : ${MainActivity_HR.outputStress}"
                        }
                        reload_btn.visibility = Button.VISIBLE

                    }
                    true
                }
            }

            val builder = AlertDialog.Builder(this)
            builder
                .setTitle("안내사항")
                .setMessage("오늘의 설문은 총 5~10분정도 소요될 예정입니다. 시간적 여유를 가지고 설문에 응답해주세요.")
                .setPositiveButton("확인", DialogInterface.OnClickListener{dialog, which ->
                    calculateSurvey()
                    val intent = Intent(this, VAS_2and3yearActivity::class.java)
                    activitylauncher.launch(intent)
                })
                .setNegativeButton("취소",null)


            btn_Survey.setOnClickListener {
                if (MainActivity_HR.timeDiff.rem(14) == 0L || (MainActivity_HR.timeDiff.div(14) > MainActivity_HR.treatFinish.div(14))){
                    builder.create()
                    builder.show()
                }
                else{
                    calculateSurvey()
                    val intent = Intent(this, VAS_2and3yearActivity::class.java)
                    activitylauncher.launch(intent)
                }
            }


            reload_btn.setOnClickListener {
                myCoroutinescope.launch {
                    calculateSurvey()
                    survey_progress.text = "연구 D+" +(MainActivity_HR.timeDiff.toInt()+1).toString()
                    rate.text = "-"
                    step.text = "-"
                    HealthService.updateHealthData(false)
                    val (HR, StepCheck) = HealthService.getHealthData()
                    Log.i(TAG, "HR : $HR , Stepcount : $StepCheck")
                    APIService.connect(this@MainActivity) {
                        myCoroutinescope.launch {
                            weatherScreen(it)
                            rate.text = HR.toInt().toString()
                            step.text = StepCheck.toString()
                            MainActivity_HR.HR = HR.toInt().toString()
                            MainActivity_HR.Steps = StepCheck.toString()
                        }
                        true
                    }
                }
            }



            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.menu)

            if (MainActivity_HR.HR != "") {
                rate.text = MainActivity_HR.HR
                btn_SamsungHealth.visibility = Button.GONE
                reload_btn.visibility = Button.VISIBLE

            }
            if (MainActivity_HR.Steps != "")
                step.text = MainActivity_HR.Steps

            requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)

        }
    }

    private fun updatePatientID(){
        val file = File("/data/data/com.example.patient_app/files/ID", "id.txt")
        if (file.exists()){
            val bufferedReader = BufferedReader(FileReader(file))
            val lines = mutableListOf<String>()

            bufferedReader.useLines { lineSequence ->
                lineSequence.forEach { line ->
                    lines.add(line)
                }
            }

            bufferedReader.close()

            if (lines.isNotEmpty()){
                lines[0] = MainActivity_HR.Patient_ID
            }

            val printWriter = PrintWriter(FileWriter(file, false))
            lines.forEach{line ->
                printWriter.println(line)
            }
            printWriter.close()
        } else {
            Toast.makeText(this, "파일이 존재하지 않습니다. 관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show()

        }

    }



    private var backPressedTime : Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime < 2000){
            ActivityCompat.finishAffinity(this)
            System.exit(0)
            return
        }

        Toast.makeText(this,"'뒤로' 버튼을 한 번 더 누르시면 앱이 종료됩니다.",Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

    private fun weatherScreen(it : StringBuilder){
        MainActivity_HR.gpsNow = it
        it.split('\n').let {
            it.forEachIndexed { index, text ->
                if (index == 0) {
                    MainActivity_HR.weatherNow = text.substring(7)
                    if (text.substring(7) == "0") {
                        val sunImg = getDrawable(R.drawable.sun_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            sunImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "1") {
                        val rainyImg = getDrawable(R.drawable.rainy_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            rainyImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "2") {
                        val rainsnowImg = getDrawable(R.drawable.rainsnow_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            rainsnowImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "3") {
                        val snowImg = getDrawable(R.drawable.snow_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            snowImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "4") {
                        val rainyImg = getDrawable(R.drawable.rainy_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            rainyImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "5") {
                        val cloudyImg = getDrawable(R.drawable.cloudy_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            cloudyImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "6") {
                        val lessrainsnowImg = getDrawable(R.drawable.lessrainsnow_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            lessrainsnowImg,
                            null,
                            null,
                            null
                        )
                    }
                    else if (text.substring(7) == "7") {
                        val snowstormImg = getDrawable(R.drawable.snowstorm_icon)
                        weather.setCompoundDrawablesWithIntrinsicBounds(
                            snowstormImg,
                            null,
                            null,
                            null
                        )
                    }
                }
                if (index == 1)
                    weather.text = " " + text
                if (index == 2)
                    local.text = text.substring(4)
                Log.i(TAG, "$text")
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

    private fun calculateSurvey(){
        MainActivity_HR.treatYear = MainActivity_HR.Patient_ID[0].toString()
        var startday = MainActivity_HR.Patient_ID.substring(8)
        val cal = Calendar.getInstance()
        cal.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
        cal[Calendar.MONTH] = startday.substring(0, 2).toInt() - 1
        cal[Calendar.DATE] = startday.substring(2, 4).toInt()
        MainActivity_HR.timeDiff =
            (System.currentTimeMillis() - cal.timeInMillis) / (1000 * 60 * 60 * 24)
        Log.i("Login", "timediff : ${MainActivity_HR.timeDiff}")
    }

}








