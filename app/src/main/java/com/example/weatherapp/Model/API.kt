package com.example.weatherapp.Model

import java.net.URL

object API {
    fun searchWeather(town: String): String {
        return URL("http://api.openweathermap.org/data/2.5/weather?q=$town&APPID=bbcf90fa8ba8a625b9a8e11691280a77").readText()
    }
}