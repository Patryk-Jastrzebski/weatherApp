package com.example.weatherapp.View
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.weatherapp.Model.CurrentWeatherResponse
import com.example.weatherapp.Model.Icon
import com.example.weatherapp.ViewModel.MainViewModel
import com.example.weatherapp.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.*
import java.net.URL
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*
import java.util.Locale
var search: String = "Gliwice"
class MainFragment: Fragment() {

    lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentTemperatureText = view.findViewById<TextView>(R.id.temperature)
        val temperatureFeelsLikeText = view.findViewById<TextView>(R.id.feels_like)
        val currentTownNameText = view.findViewById<TextView>(R.id.town_name)
        val descriptionText = view.findViewById<TextView>(R.id.description)
        val windSpeedText = view.findViewById<TextView>(R.id.wind_speed)
        val humidityText = view.findViewById<TextView>(R.id.humidity)
        val visibilityText = view.findViewById<TextView>(R.id.visibility)
        val sunriseTimeText = view.findViewById<TextView>(R.id.sunrise_time)
        val sunsetTimeText = view.findViewById<TextView>(R.id.sunset_time)
        val pressureText = view.findViewById<TextView>(R.id.pressure)
        val currentDateText = view.findViewById<TextView>(R.id.current_date)
        val currentIcon = view.findViewById<ImageView>(R.id.weatherIcon)

        view.findViewById<Button>(R.id.lessDetails).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_mainFragment_to_bigFontFragment)
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            view.searchViewLocation.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextChange(newText: String?): Boolean {

                    return false
                }

                override fun onQueryTextSubmit(newText: String?): Boolean {
                    lifecycleScope.launch(Dispatchers.IO) {
                    search = newText!!
                    val data = searchWeather(search)
                    val gson = Gson()
                    val forecast = gson.fromJson(data,CurrentWeatherResponse::class.java)
                    withContext(Dispatchers.Main) {
                        val currentTemp: Int = (forecast.main.temp).toInt() - 274
                        val tempFeels = "Feels like " + (forecast.main.feelsLike - 274).toInt() + "°"
                        val weatherDescription = forecast.weather[0].description
                        val windSpeed = forecast.wind.speed
                        val humidity = forecast.main.humidity
                        val visibility = forecast.visibility/100
                        val pressure = forecast.main.pressure
                        val currentDate: String =
                            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

                        temperatureFeelsLikeText.text = tempFeels
                        currentTemperatureText.text = "$currentTemp°"
                        currentTownNameText.text = "${forecast.name}, Poland"
                        descriptionText.text = weatherDescription
                        windSpeedText.text = "$windSpeed m/h"
                        humidityText.text = "$humidity %"
                        visibilityText.text = "$visibility %"
                        sunriseTimeText.text = convert(forecast.sys.sunrise)
                        sunsetTimeText.text = convert(forecast.sys.sunset)
                        pressureText.text = "$pressure hPa"
                        currentDateText.text = "$currentDate"
                        currentIcon.setImageResource(Icon.weatherIcon(forecast.weather[0].icon))}}
                    return false
                }
            })

            val data = searchWeather(search)
            val gson = Gson()
            val forecast = gson.fromJson(data,CurrentWeatherResponse::class.java)
            withContext(Dispatchers.Main) {
                val currentTemp: Int = (forecast.main.temp).toInt() - 274
                val tempFeels = "Feels like " + (forecast.main.feelsLike - 274).toInt() + "°"
                val weatherDescription = forecast.weather[0].description
                val windSpeed = forecast.wind.speed
                val humidity = forecast.main.humidity
                val visibility = forecast.visibility/100
                val pressure = forecast.main.pressure
                val currentDate: String =
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

                temperatureFeelsLikeText.text = tempFeels
                currentTemperatureText.text = "$currentTemp°"
                currentTownNameText.text = "${forecast.name}, Poland"
                descriptionText.text = weatherDescription
                windSpeedText.text = "$windSpeed m/h"
                humidityText.text = "$humidity %"
                visibilityText.text = "$visibility %"
                sunriseTimeText.text = convert(forecast.sys.sunrise)
                sunsetTimeText.text = convert(forecast.sys.sunset)
                pressureText.text = "$pressure hPa"
                currentDateText.text = "$currentDate"
                currentIcon.setImageResource(Icon.weatherIcon(forecast.weather[0].icon))
                }}}}

@RequiresApi(Build.VERSION_CODES.O)
private fun convert(s: Int): String {

    val dt = "${Instant.ofEpochSecond(s.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalTime().hour}:${Instant.ofEpochSecond(s.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalTime().minute}"
    return dt
}
private fun searchWeather(town: String): String {
    return URL("http://api.openweathermap.org/data/2.5/weather?q=$town,pl&APPID=bbcf90fa8ba8a625b9a8e11691280a77").readText()
}