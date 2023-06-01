package com.example.patient_app.Activity

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.patient_app.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileReader
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val activitylauncher = openActivityResultLauncher()

        btn_login.setOnClickListener {
            var file = File("/data/data/com.example.patient_app/files/ID", "id.txt")
            if(file.exists()) {
                readID(file)
                calculateSurvey()
                Toast.makeText(this, "ID : ${MainActivity_HR.Patient_ID} Login", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, MainActivity::class.java)
                activitylauncher.launch(intent)
            }
            else
                Toast.makeText(this, "Please create an ID", Toast.LENGTH_SHORT).show()

        }


        btn_ifNoID.setOnClickListener {
            var intent = Intent(this, Survey_1Activity::class.java)
            startActivity((intent))
        }

    }

    private fun readID(file : File){
        var fileiter = FileReader(file).readLines().iterator()
        MainActivity_HR.Patient_ID = fileiter.next()
        MainActivity_HR.treatFinish = fileiter.next().toInt()
        Log.i("Login","id : ${MainActivity_HR.Patient_ID}")
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

    private fun exitProgram() {
        moveTaskToBack(true);
        if (Build.VERSION.SDK_INT >= 21) {
            // 액티비티 종료 + 태스크 리스트에서 지우기
            finishAndRemoveTask()
        } else {
            // 액티비티 종료
            finish()
        }
        exitProcess(0)
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
                    add(Calendar.DATE, 1)
                }

                var alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                if(alarmManager != null){
                    val intent = Intent(this, AlarmReceiver::class.java)
                    val alarmIntent = PendingIntent.getBroadcast(
                        this,
                        1,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )

                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP, calender.timeInMillis,
                        alarmIntent
                    )

                    Log.i("Login","AlarmManager is called")
                    Toast.makeText(this, "설문 진행도가 저장되었습니다.", Toast.LENGTH_LONG).show()
                }

                exitProgram()
            }
        }
        return resultLauncher
    }
}