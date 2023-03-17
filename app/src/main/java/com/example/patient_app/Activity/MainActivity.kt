package com.example.patient_app.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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


        survey_progress.text = "연구 D+" +(MainActivity_HR.timeDiff.toInt()+1).toString()

        btn_SamsungHealth.visibility = Button.VISIBLE
        btn_Survey.visibility = Button.INVISIBLE
        survey_end.visibility = TextView.INVISIBLE



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
                        MainActivity_HR.Steps = StepCheck.toInt().toString()
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
                    MainActivity_HR.treatYear = MainActivity_HR.Patient_ID[0].toString()
                    var startday = MainActivity_HR.Patient_ID.substring(8)
                    val cal = Calendar.getInstance()
                    cal.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
                    cal[Calendar.MONTH] = startday.substring(0, 2).toInt() - 1
                    cal[Calendar.DATE] = startday.substring(2, 4).toInt()
                    MainActivity_HR.timeDiff =
                        (System.currentTimeMillis() - cal.timeInMillis) / (1000 * 60 * 60 * 24)
                    Log.i(TAG, "${MainActivity_HR.timeDiff.rem(7) == 0L}")
                    if (MainActivity_HR.treatYear == "2" || MainActivity_HR.treatYear == "3") {
                        val intent = Intent(this, VAS_2and3yearActivity::class.java)
                        activitylauncher.launch(intent)
                    } else
                        Toast.makeText(this, "ID 오류가 발생하였습니다.", Toast.LENGTH_SHORT)
                })
                .setNegativeButton("취소",null)


            btn_Survey.setOnClickListener {
                if (MainActivity_HR.timeDiff.rem(14) == 0L){
                    builder.create()
                    builder.show()
                }
                else{
                MainActivity_HR.treatYear = MainActivity_HR.Patient_ID[0].toString()
                var startday = MainActivity_HR.Patient_ID.substring(8)
                val cal = Calendar.getInstance()
                cal.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
                cal[Calendar.MONTH] = startday.substring(0, 2).toInt() - 1
                cal[Calendar.DATE] = startday.substring(2, 4).toInt()
                MainActivity_HR.timeDiff =
                    (System.currentTimeMillis() - cal.timeInMillis) / (1000 * 60 * 60 * 24)
                Log.i(TAG, "${MainActivity_HR.timeDiff.rem(7) == 0L}")
                if (MainActivity_HR.treatYear == "2" || MainActivity_HR.treatYear == "3") {
                    val intent = Intent(this, VAS_2and3yearActivity::class.java)
                    activitylauncher.launch(intent)
                } else
                    Toast.makeText(this, "ID 오류가 발생하였습니다.", Toast.LENGTH_SHORT)
            }
            }


            reload_btn.setOnClickListener {

                myCoroutinescope.launch {
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
                            MainActivity_HR.Steps = StepCheck.toInt().toString()
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
        it.split('\n').let {
            it.forEachIndexed { index, text ->
                if (index == 0) {
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
                var calender = Calendar.getInstance().apply {
                    timeInMillis = System.currentTimeMillis()
                    set(Calendar.HOUR_OF_DAY,12)
                    set(Calendar.MINUTE,0)
                    set(Calendar.SECOND,0)
                }

                var alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                if(alarmManager != null){
                    val intent = Intent(this, AlarmReceiver::class.java)
                    val alarmIntent = PendingIntent.getBroadcast(
                        this,
                        1,
                        intent,
                        PendingIntent.FLAG_MUTABLE
                    )

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, calender.timeInMillis,
                        AlarmManager.INTERVAL_DAY, alarmIntent
                    )

                    Toast.makeText(this@MainActivity, "알람이 저장되었습니다.", Toast.LENGTH_LONG).show()
                }

            }
        }
        return resultLauncher
    }
}








