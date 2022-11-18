package com.example.patient_app.WeatherAPI

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherAPI (
    private var pageNo: String = "1",
    private var numOfRows: String = "10",
    private var loc_x: String = "56",
    private var loc_y: String = "119"
){
    private val TAG = "Weather"

    companion object {
        private const val URL = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"
        private const val serviceKey = "lNP0DInrZFHTZI3zka0V8Ye/DhQD/AJjmbqp/gMc6bVQd1q12G9nLBw+jEsBa7ygZKy1pjkkdkRMxueGYPtAjA=="
        private const val dataType = "XML"
        private var dateFormat = SimpleDateFormat("yyyyMMdd_HHmm")
        var dateNow = dateFormat.format(Date(System.currentTimeMillis()))
        private var base_date = dateNow.substring(0,dateNow.indexOf('_'))
        private var base_time = dateNow.substring(dateNow.indexOf('_')+1)
        private var urlInit = StringBuilder("$URL")
    }

    fun setInit() {
        urlInit.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=$serviceKey") /*Service Key*/
        urlInit.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("$numOfRows", "UTF-8")) /*한 페이지 결과 수*/
        urlInit.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("$pageNo", "UTF-8")) /*페이지번호*/
        urlInit.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("$dataType", "UTF-8")) /*요청자료형식(XML/JSON) Default: XML*/
        urlInit.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("$base_date", "UTF-8")) /*‘21년 6월 28일발표*/
        urlInit.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("$base_time", "UTF-8")) /*05시 발표*/
        urlInit.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("$loc_x", "UTF-8")) /*예보지점의 X 좌표값*/
        urlInit.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("$loc_y", "UTF-8")) /*예보지점의 Y 좌표값*/
        Log.i(TAG, urlInit.toString())

    }
    fun runAPI(){
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
        val sb = java.lang.StringBuilder()
        var line: String?
        while (rd.readLine().also { line = it } != null) {
            sb.append(line)
        }
        rd.close()
        conn.disconnect()
        Log.i(TAG,sb.toString())
    }
}