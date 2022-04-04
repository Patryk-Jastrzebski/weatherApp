package com.example.weatherapp.Model

import com.example.weatherapp.R

object Icon {

    fun weatherIcon(icon: String): Int {
        when (icon) {
            "01d" -> return R.mipmap.sun
            "01n" -> return R.mipmap.sun
            "02d" -> return R.mipmap.cloudy
            "02n" -> return R.mipmap.cloudy
            "03d" -> return R.mipmap.clouds
            "03n" -> return R.mipmap.clouds
            "04d" -> return R.mipmap.storm
            "04n" -> return R.mipmap.storm
            "09d" -> return R.mipmap.snowflake
            "09n" -> return R.mipmap.snowflake
            "10d" -> return R.mipmap.rainy
            "10n" -> return R.mipmap.rainy
            "11d" -> return R.mipmap.storm
            "11n" -> return R.mipmap.storm
            "13d" -> return R.mipmap.snowflake
            "13n" -> return R.mipmap.snowflake
            "50d" -> return R.mipmap.windy
            else -> return R.mipmap.cloudy
        }
    }

}