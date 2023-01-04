package com.example.patient_app.WeatherAPI


import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

class WeatherAPI (
    private var loc_x: Double,
    private var loc_y: Double,
    private var json_string : (StringBuilder) -> Boolean
){
    private val TAG = "Weather"
    private lateinit var convertedLoc : LatXLngY

    companion object {
        private const val URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"
        private const val serviceKey = "lNP0DInrZFHTZI3zka0V8Ye%2FDhQD%2FAJjmbqp%2FgMc6bVQd1q12G9nLBw%2BjEsBa7ygZKy1pjkkdkRMxueGYPtAjA%3D%3D"
        private const val dataType = "JSON"
        private const val pageNo: String = "1"
        private const val numOfRows: String = "10"
        private var dateFormat = SimpleDateFormat("yyyyMMdd_HHmm")
        private var dateRaw = Date(System.currentTimeMillis())
        private var dateNow = dateFormat.format(dateRaw)
        private var base_date = dateNow.substring(0,dateNow.indexOf('_'))
        private var base_time = dateNow.substring(dateNow.indexOf('_')+1)
        private var urlInit = StringBuilder()
    }

    fun convertGRIDDtoGPS(){
        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
        val RE = 6371.00877 // 지구 반경(km)
        val GRID = 5.0 // 격자 간격(km)
        val SLAT1 = 30.0 // 투영 위도1(degree)
        val SLAT2 = 60.0 // 투영 위도2(degree)
        val OLON = 126.0 // 기준점 경도(degree)
        val OLAT = 38.0 // 기준점 위도(degree)
        val XO = 43.0 // 기준점 X좌표(GRID)
        val YO = 136.0 // 기1준점 Y좌표(GRID)


        val DEGRAD = Math.PI / 180.0
//        val RADDEG = 180.0 / Math.PI
        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD
        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn
        var ro = Math.tan(Math.PI * 0.25 + olat * 0.5)
        ro = re * sf / Math.pow(ro, sn)

        var ra = Math.tan(Math.PI * 0.25 + loc_x * DEGRAD * 0.5)
        ra = re * sf / Math.pow(ra, sn)
        var theta: Double = loc_y * DEGRAD - olon
        if (theta > Math.PI) theta -= 2.0 * Math.PI
        if (theta < -Math.PI) theta += 2.0 * Math.PI
        theta *= sn
        convertedLoc = LatXLngY(loc_x, loc_y, Math.floor(ra * Math.sin(theta) + XO + 0.5).toUInt(), Math.floor(ro - ra * Math.cos(theta) + YO + 0.5).toUInt())
        Log.i(TAG,"convertGRIDDtoGPS called")
    }
    data class LatXLngY(
        var lat : Double,
        var lng : Double,
        var x : UInt,
        var y : UInt
    )
    private fun convert1hourago(){
        dateRaw = Date(dateRaw.time +(1000*60*60*-1))
        dateNow = dateFormat.format(dateRaw)
        base_date = dateNow.substring(0,dateNow.indexOf('_'))
        base_time = dateNow.substring(dateNow.indexOf('_')+1)
    }

    fun setInit() {
        urlInit.clear()
        urlInit = StringBuilder("$URL")
        urlInit.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=$serviceKey") /*Service Key*/
        urlInit.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("$numOfRows", "UTF-8")) /*한 페이지 결과 수*/
        urlInit.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("$pageNo", "UTF-8")) /*페이지번호*/
        urlInit.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("$dataType", "UTF-8")) /*요청자료형식(XML/JSON) Default: XML*/
        urlInit.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("$base_date", "UTF-8")) /*‘21년 6월 28일발표*/
        urlInit.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("$base_time", "UTF-8")) /*05시 발표*/
        if(!::convertedLoc.isInitialized){
            convertGRIDDtoGPS()
        }
        urlInit.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("${convertedLoc.x}", "UTF-8")) /*예보지점의 X 좌표값*/
        urlInit.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("${convertedLoc.y}", "UTF-8")) /*예보지점의 Y 좌표값*/
        Log.i(TAG, "SetInit : ${urlInit}")
    }

    fun runAPI() : Boolean{
        var taskChecker : Boolean = true
        val url = URL(urlInit.toString())
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Content-type", "application/xml")
        Log.i(TAG,"Response code: ${conn.responseCode}")

        val rd: BufferedReader
        if (conn.responseCode in 200..300) {
            rd = BufferedReader(InputStreamReader(conn.inputStream))
        } else {
            rd = BufferedReader(InputStreamReader(conn.errorStream))
        }
        val sb = StringBuilder()
        var line: String?
        while (rd.readLine().also { line = it } != null) {
            sb.append(line)
        }
        Log.i(TAG,"$rd")
        rd.close()
        conn.disconnect()
        Log.i(TAG,sb.toString())
//        xml_string(sb.toString())
        val json_parser = JSONparsing(sb.toString()) { data, tf ->
            if(tf) {
                json_string(data)
                taskChecker = false
            }
            else {
                convert1hourago()
            }
            true
        }
        json_parser.readEntry()
        return taskChecker
    }

}