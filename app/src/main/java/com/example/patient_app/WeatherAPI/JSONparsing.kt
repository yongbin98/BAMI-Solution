package com.example.patient_app.WeatherAPI

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

class JSONparsing(
    private val json_data : String,
    private val parsing_data : (StringBuilder,Boolean) -> Boolean
) {
    private var jsonObject = JSONTokener(json_data).nextValue() as JSONObject
    private val TAG = "JSON"

    fun readEntry(){
        var tmp = jsonObject.get("response")
        jsonObject = JSONTokener(tmp.toString()).nextValue() as JSONObject
        tmp = jsonObject.getString("header")
        val getResultcode = JSONTokener(tmp.toString()).nextValue() as JSONObject

        if(getResultcode.getString("resultCode").equals("00")) {
            tmp = jsonObject.get("body")
            jsonObject = JSONTokener(tmp.toString()).nextValue() as JSONObject
            tmp = jsonObject.get("items")
            jsonObject = JSONTokener(tmp.toString()).nextValue() as JSONObject

            var jsonArray = jsonObject.getJSONArray("item")

            var jsonChildObj : JSONObject
            var jsonCategoryName : String
            val jsonStringbuilder = StringBuilder()
            for (i in 0 until jsonArray.length()){
                jsonChildObj = jsonArray.getJSONObject(i)
                jsonCategoryName = jsonChildObj.getString("category")
                if(jsonCategoryName == "PTY"){
                    jsonStringbuilder.append("강수형태 : ${jsonChildObj.getString("obsrValue")}\n")
                }
                if(jsonCategoryName == "T1H"){
                    jsonStringbuilder.append("${jsonChildObj.getString("obsrValue")}\n")
                }
            }
            parsing_data(jsonStringbuilder,true)
        }
        else{
            parsing_data(StringBuilder(),false)
        }
    }
}