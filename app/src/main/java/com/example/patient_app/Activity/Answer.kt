package com.example.patient_app.Activity

import com.example.patient_app.SFTP.FileType


class BasicInfo {
    companion object {
        var year = 1999
        var month = "1"
        var day = "28"
        var gender = ""
        var height = ""
        var weight = ""

        //음주
        var alcohol = ""
        var alcohol_num = ""
        var alcohol_treat = ""

        //흡연
        var smoking = ""
        var smoking_num = ""
        var smoking_treat = ""

        //운동
        var exercise = ""
        var exercise_num = ""
        var exercise_treat = ""

        //우울
        var depressed = ""
        var depressed_num = ""
        var depressed_treat = ""

        //불안
        var unrest = ""
        var unrest_num = ""
        var unrest_treat = ""

        //고혈압
        var hypertension = ""
        var hypertension_num = ""
        var hypertension_treat = ""

        //당뇨
        var diabetes = ""
        var diabetes_num = ""
        var diabetes_treat = ""

        //정신질환
        var mental = ""
        var mental_num = ""
        var mental_treat = ""

        //치료 기간
        var length = ""
    }
}


class YMRSanswer{
    companion object{
        var YMRS1 = arrayOf("","","","","")
        var YMRS2 = arrayOf("","","","")
        var YMRS3 = arrayOf("","","")
        var YMRS4 = arrayOf("","")
        var YMRS5 = arrayOf("","","","")
        var YMRS6 = arrayOf("","","","")
        var YMRS7 = arrayOf("","","","","","")
        var YMRS8 = arrayOf("","","","","","","","","","")
        var YMRS9 = arrayOf("","","","")
        var YMRS10 = arrayOf("","","","","")
        var YMRS11 = ""
    }
}

class PHQanswer{
    companion object{
        var PHQ1_ans = 0
        var PHQ2_ans = 0
        var PHQ3_ans = 0
        var PHQ4_ans = 0
        var PHQ5_ans = 0
        var PHQ6_ans = 0
        var PHQ7_ans = 0
        var PHQ8_ans = 0
        var PHQ9_ans = 0
    }
}

class GADanswer{
    companion object{
        var GAD1_ans = 0
        var GAD2_ans = 0
        var GAD3_ans = 0
        var GAD4_ans = 0
        var GAD5_ans = 0
        var GAD6_ans = 0
        var GAD7_ans = 0
    }
}

class Insomnia{
    companion object{
        var insomnia1 = arrayOf("","","")
        var insomnia2345 = arrayOf("","","","")
    }
}

class Stress{
    companion object{
        var stress = arrayOf("","","","","","","","","")
    }
}

class VAS{
    companion object{
        var vas = arrayOf("","","","","")
    }
}

class WHOQ{
    companion object{
        var WHOQ = arrayOf("","","","","","","","","","","","","","","","","","","","","","","","","","")
    }
}

class Mars{
    companion object{
        var Mars = arrayOf("","","","","","","","","","",)
        var Score = ""
    }
}

class Hamilton{
    companion object{
        var hamilton = arrayOf("","","","","","","","","","","","","","","","","","","","","")
    }
}

class SSIanswer{
    companion object{
        var SSI1_ans : Int ?= null
        var SSI2_ans : Int ?= null
        var SSI3_ans : Int ?= null
        var SSI4_ans : Int ?= null
        var SSI5_ans : Int ?= null
        var SSI6_ans : Int ?= null
        var SSI7_ans : Int ?= null
        var SSI8_ans : Int ?= null
        var SSI9_ans : Int ?= null
        var SSI10_ans : Int ?= null
        var SSI11_ans : Int ?= null
        var SSI12_ans : Int ?= null
        var SSI13_ans : Int ?= null
        var SSI14_ans : Int ?= null
        var SSI15_ans : Int ?= null
        var SSI16_ans : Int ?= null
        var SSI17_ans : Int ?= null
        var SSI18_ans : Int ?= null
        var SSI19_ans : Int ?= null
    }
}


class MainActivity_HR{
    companion object{
        var HR = ""
        var Steps = ""
        var Patient_ID = "219802100216"
        var treatYear = ""
        var timeDiff = 0L
    }


}

