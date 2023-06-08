package com.example.patient_app.Activity

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.patient_app.R
import com.example.patient_app.SFTP.File
import com.example.patient_app.SFTP.FileType
import com.example.patient_app.ble.SFTP
import com.example.patient_app.samsungHealth.HealthService
import kotlinx.android.synthetic.main.activity_thankyou.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Thankyou : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou)

        val dialog = LoadingDialog(this)
        dialog.setContentView(R.layout.dialog_loading)

        val myCoroutinescope = CoroutineScope(Dispatchers.IO)

        dialog.show()

        myCoroutinescope.launch {
            HealthService.updateHealthData(true)
            HealthService.getHealthData()
            saveSurvey()
            var sftp = SFTP()
            sftp.connect()
            sftp.upload(File.files)
            sftp.disconnect()
            File.delete()
            rewriteID()

        dialog.dismiss()

            end_btn.setOnClickListener {
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }





    private fun rewriteID(){
        var file = java.io.File("/data/data/com.example.patient_app/files/ID", "id.txt")
        if(file.exists())
            file.delete()

        val printWriter = PrintWriter(file)
        printWriter.println(MainActivity_HR.Patient_ID)
        printWriter.println(MainActivity_HR.timeDiff + 1)
        printWriter.close()
        MainActivity_HR.treatFinish = MainActivity_HR.timeDiff.toInt() + 1
    }


    private fun saveSurvey(){
        var file = File(FileType.startCharOf('S'))
        file.write("birth,${BasicInfo.year},${BasicInfo.month},${BasicInfo.day}\n")
        file.write("gender,${BasicInfo.gender}\n")
        file.write("gender,${BasicInfo.height}cm\n")
        file.write("weight,${BasicInfo.weight}kg\n")
        file.write("alcohol,${BasicInfo.alcohol},${BasicInfo.alcohol_num},${BasicInfo.alcohol_treat}\n")
        file.write("smoking,${BasicInfo.smoking},${BasicInfo.smoking_num},${BasicInfo.smoking_treat}\n")
        file.write("depressed,${BasicInfo.depressed},${BasicInfo.depressed_num},${BasicInfo.depressed_treat}\n")
        file.write("unrest,${BasicInfo.unrest},${BasicInfo.unrest_num},${BasicInfo.unrest_treat}\n")
        file.write("hypertension,${BasicInfo.hypertension},${BasicInfo.hypertension_num},${BasicInfo.hypertension_treat}\n")
        file.write("diabetes,${BasicInfo.diabetes},${BasicInfo.diabetes_num},${BasicInfo.diabetes_treat}\n")
        file.write("mental,${BasicInfo.mental},${BasicInfo.mental_num},${BasicInfo.mental_treat}\n")
        file.write("length,${BasicInfo.length}\n")

        var tmpString = ""
        for(i in 0 until YMRSanswer.YMRS1.size)
            tmpString += YMRSanswer.YMRS1[i]+','
        file.write("YMRSanswer1,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS2.size)
            tmpString += YMRSanswer.YMRS2[i]+','
        file.write("YMRSanswer2,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS3.size)
            tmpString += YMRSanswer.YMRS3[i]+','
        file.write("YMRSanswer3,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS4.size)
            tmpString += YMRSanswer.YMRS4[i]+','
        file.write("YMRSanswer4,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS5.size)
            tmpString += YMRSanswer.YMRS5[i]+','
        file.write("YMRSanswer5,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS6.size)
            tmpString += YMRSanswer.YMRS6[i]+','
        file.write("YMRSanswer6,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS7.size)
            tmpString += YMRSanswer.YMRS7[i]+','
        file.write("YMRSanswer7,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS8.size)
            tmpString += YMRSanswer.YMRS8[i]+','
        file.write("YMRSanswer8,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS9.size)
            tmpString += YMRSanswer.YMRS9[i]+','
        file.write("YMRSanswer9,${tmpString}\n")

        tmpString = ""
        for(i in 0 until YMRSanswer.YMRS10.size)
            tmpString += YMRSanswer.YMRS10[i]+','
        file.write("YMRSanswer10,${tmpString}\n")

        tmpString = YMRSanswer.YMRS11
        file.write("YMRSanswer11,${tmpString}\n")

        tmpString = "PHQanswer,"
        for(i in 0 until PHQanswer.PHQ.size)
            tmpString += PHQanswer.PHQ[i]+','
        file.write("${tmpString}\n")

        tmpString = "GADanswer,"
        for(i in 0 until GADanswer.GAD.size)
            tmpString += GADanswer.GAD[i]+','
        file.write("${tmpString}\n")

        tmpString = ""
        for(i in 0 until Insomnia.insomnia1.size)
            tmpString += Insomnia.insomnia1[i]+','
        file.write("insomnia1,${tmpString}\n")

        tmpString = ""
        for(i in 0 until Insomnia.insomnia2345.size)
            tmpString += Insomnia.insomnia2345[i]+','
        file.write("insomnia2345,${tmpString}\n")

        tmpString = ""
        for(i in 0 until Stress.stress.size)
            tmpString += Stress.stress[i]+','
        file.write("Stress,${tmpString}\n")

        tmpString = ""
        for(i in 0 until VAS.vas.size)
            tmpString += VAS.vas[i]+','
        file.write("VAS,${tmpString}\n")

        tmpString = ""
        for(i in 0 until WHOQ.WHOQ.size)
            tmpString += WHOQ.WHOQ[i]+','
        file.write("WHOQOL,${tmpString}\n")

        tmpString = ""
        for(i in 0 until Mars.Mars.size)
            tmpString += Mars.Mars[i]+','
        file.write("MARS,${tmpString}\n")

        file.write("MARS_Score,${Mars.Score}\n")

        tmpString = ""
        for(i in 0 until Hamilton.hamilton.size)
            tmpString += Hamilton.hamilton[i]+','
        file.write("21Hamilton,${tmpString}\n")

        if(SSIanswer.SSI1_ans != null) {
            file.write(
                "SSIanswer,${SSIanswer.SSI1_ans},${SSIanswer.SSI2_ans},${SSIanswer.SSI3_ans},${SSIanswer.SSI4_ans},${SSIanswer.SSI5_ans}," +
                        "${SSIanswer.SSI6_ans},${SSIanswer.SSI7_ans},${SSIanswer.SSI8_ans},${SSIanswer.SSI9_ans},${SSIanswer.SSI10_ans},${SSIanswer.SSI11_ans}," +
                        "${SSIanswer.SSI12_ans},${SSIanswer.SSI13_ans},${SSIanswer.SSI14_ans},${SSIanswer.SSI15_ans},${SSIanswer.SSI16_ans},${SSIanswer.SSI17_ans}," +
                        "${SSIanswer.SSI18_ans},${SSIanswer.SSI19_ans}\n"
            )
        }
        else
            file.write("SSIanswer,\n")

        MainActivity_HR.gpsNow.split('\n').let {
            var tmpIter = it.iterator()
            var Itercount = 0
            while(tmpIter.hasNext()){
                var fileNext = tmpIter.next()
                if(Itercount == 0)
                    file.write(fileNext+"\n")
                else if(Itercount == 1)
                    file.write("기온 : "+fileNext+"\n")
                else if(Itercount == 2)
                    file.write("위치 : "+fileNext+"\n")
                Log.i("Thx" , fileNext)
                Itercount++
            }
        }
        file.close()
    }
}