package com.example.patient_app.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.patient_app.DNN.TFlite
import com.example.patient_app.R
import com.example.patient_app.SFTP.File
import com.example.patient_app.SFTP.FileType
import com.example.patient_app.SFTP.WebDAV
import com.example.patient_app.samsungHealth.HealthService
import kotlinx.android.synthetic.main.activity_thankyou.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.FileReader
import java.io.PrintWriter


class Thankyou : AppCompatActivity() {
    private val TAG = "Thankyou"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thankyou)

        MainActivity_HR.treatFinish = MainActivity_HR.timeDiff.toInt() + 1
        val dialog = LoadingDialog(this)
        dialog.setContentView(R.layout.dialog_loading)

        val myCoroutinescope = CoroutineScope(Dispatchers.IO)

        val webDAV = WebDAV()

        // Example input data, adjust based on your model's input requirements

        var twodaybefore = java.io.File("/data/data/com.example.patient_app/files/ID", (MainActivity_HR.treatFinish-1).toString()+".txt")
        if(!twodaybefore.exists()){
            twodaybefore = java.io.File("/data/data/com.example.patient_app/files/ID", (MainActivity_HR.treatFinish-0).toString()+".txt")
        }
        val onedaybefore = java.io.File("/data/data/com.example.patient_app/files/ID", (MainActivity_HR.treatFinish).toString()+".txt")

        if(twodaybefore.exists() and onedaybefore.exists()) {
            val inputA_1 = readdaybefore(twodaybefore)
            Log.i(TAG, "inputA_1: ${inputA_1.joinToString(", ")}")
            val inputA_2 = readdaybefore(onedaybefore)
            Log.i(TAG, "inputA_2: ${inputA_1.joinToString(", ")}")

            val inputA = arrayOf(arrayOf(inputA_1, inputA_2))
            val inputB = arrayOf(
                floatArrayOf(
                    normalize(20f, 58f, MainActivity_HR.age),
                    normalize(0f, 1f, MainActivity_HR.sex),
                    normalize(17.1468f, 32.9660f, MainActivity_HR.bmi),
                    normalize(2f, 5f, MainActivity_HR.edu),
                    normalize(0f, 3f, MainActivity_HR.drink)
                )
            )

            val tfliteModel = TFlite(this)
            val output = tfliteModel.runInference(inputA, inputB)
            storeOutput(output)
            Log.i(TAG, "Output : ${output[0]}")
        }

        dialog.show()

        myCoroutinescope.launch {
            HealthService.updateHealthData(true)
            HealthService.getHealthData()
            saveSurvey()

            webDAV.upload(File.files)
            File.delete()
            rewriteID()
            storeData()
            dialog.dismiss()
            end_btn.setOnClickListener {
                val intent = Intent()
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }



    private fun normalize(min:Float,max:Float,data:Float): Float {
        return (data - min) / (max - min)
    }


    private fun rewriteID(){
        var file = java.io.File("/data/data/com.example.patient_app/files/ID", "id.txt")
        if(file.exists())
            file.delete()

        val printWriter = PrintWriter(file)
        printWriter.println(MainActivity_HR.Patient_ID)
        printWriter.println(MainActivity_HR.timeDiff+1)
        printWriter.println(MainActivity_HR.age)
        printWriter.println(MainActivity_HR.sex)
        printWriter.println(MainActivity_HR.bmi)
        printWriter.println(MainActivity_HR.edu)
        printWriter.println(MainActivity_HR.drink)
        printWriter.close()
    }

    private fun storeData(){
        var file = java.io.File("/data/data/com.example.patient_app/files/ID", MainActivity_HR.treatFinish.toString() + ".txt")
        if(file.exists())
            file.delete()

        val printWriter = PrintWriter(file)
        printWriter.println(MainActivity_HR.Steps)
        printWriter.println(MainActivity_HR.HR)
        printWriter.println(MainActivity_HR.weatherNow)
        printWriter.println(MainActivity_HR.vasSleep)
        printWriter.println(MainActivity_HR.vasAnxDep)
        printWriter.println(MainActivity_HR.vasStress)
        printWriter.close()
    }

    private fun storeOutput(output: FloatArray) {
        var file = java.io.File("/data/data/com.example.patient_app/files/ID", "stressOutput.txt")
        if(file.exists())
            file.delete()

        val printWriter = PrintWriter(file)
        printWriter.println(output[0].toInt())
        printWriter.close()
    }

    private fun readdaybefore(file : java.io.File) : FloatArray {
        var fileiter = FileReader(file).readLines().iterator()
        var Steps = fileiter.next().toFloat()
        var HR = fileiter.next().toFloat()
        var weatherNow = fileiter.next().toFloat()
        var vasSleep = fileiter.next().toFloat()
        var vasAnxDep = fileiter.next().toFloat()
        var vasStress = fileiter.next().toFloat()

        if(HR == 0f)
            HR = 80f

        return floatArrayOf(Steps,MainActivity_HR.avgSpeed,MainActivity_HR.LFHF,HR,weatherNow,vasSleep,vasAnxDep,vasStress)
    }

    private fun saveSurvey(){
        var file = File(FileType.startCharOf('S'))
        file.write("birth,${BasicInfo.year},${BasicInfo.month},${BasicInfo.day}\n")
        file.write("gender,${BasicInfo.gender}\n")
        file.write("height,${BasicInfo.height}cm\n")
        file.write("weight,${BasicInfo.weight}kg\n")
        file.write("education,${BasicInfo.Education}\n")
        file.write("alcohol,${BasicInfo.alcohol},${BasicInfo.alcohol_num},${BasicInfo.alcohol_treat}\n")
        file.write("smoking,${BasicInfo.smoking},${BasicInfo.smoking_num},${BasicInfo.smoking_treat}\n")
        file.write("exercise,${BasicInfo.exercise},${BasicInfo.exercise_num},${BasicInfo.exercise_treat}\n")
        file.write("depressed,${BasicInfo.depressed},${BasicInfo.depressed_num},${BasicInfo.depressed_treat}\n")
        file.write("unrest,${BasicInfo.unrest},${BasicInfo.unrest_num},${BasicInfo.unrest_treat}\n")
        file.write("hypertension,${BasicInfo.hypertension},${BasicInfo.hypertension_num},${BasicInfo.hypertension_treat}\n")
        file.write("diabetes,${BasicInfo.diabetes},${BasicInfo.diabetes_num},${BasicInfo.diabetes_treat}\n")
        file.write("mental,${BasicInfo.mental},${BasicInfo.mental_num},${BasicInfo.mental_treat}\n")

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

//        tmpString = ""
//        for(i in 0 until WHOQ.WHOQ.size)
//            tmpString += WHOQ.WHOQ[i]+','
//        file.write("WHOQOL,${tmpString}\n")
//
//        tmpString = ""
//        for(i in 0 until Mars.Mars.size)
//            tmpString += Mars.Mars[i]+','
//        file.write("MARS,${tmpString}\n")
//
//        file.write("MARS_Score,${Mars.Score}\n")

//        tmpString = ""
//        for(i in 0 until Hamilton.hamilton.size)
//            tmpString += Hamilton.hamilton[i]+','
//        file.write("21Hamilton,${tmpString}\n")

//        if(SSIanswer.SSI1_ans != null) {
//            file.write(
//                "SSIanswer,${SSIanswer.SSI1_ans},${SSIanswer.SSI2_ans},${SSIanswer.SSI3_ans},${SSIanswer.SSI4_ans},${SSIanswer.SSI5_ans}," +
//                        "${SSIanswer.SSI6_ans},${SSIanswer.SSI7_ans},${SSIanswer.SSI8_ans},${SSIanswer.SSI9_ans},${SSIanswer.SSI10_ans},${SSIanswer.SSI11_ans}," +
//                        "${SSIanswer.SSI12_ans},${SSIanswer.SSI13_ans},${SSIanswer.SSI14_ans},${SSIanswer.SSI15_ans},${SSIanswer.SSI16_ans},${SSIanswer.SSI17_ans}," +
//                        "${SSIanswer.SSI18_ans},${SSIanswer.SSI19_ans}\n"
//            )
//        }
//        else
//            file.write("SSIanswer,\n")

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
