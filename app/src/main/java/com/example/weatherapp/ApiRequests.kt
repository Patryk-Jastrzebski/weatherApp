package com.example.weatherapp

import com.example.weatherapp.Model.CurrentWeatherResponse

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Path


interface ApiRequests {

    @GET("?q={Gliwice},pl&APPID=b31c2399a91bd7f2a5019bbe3fa1e049")
    fun getTemp(
        @Path("city") city: String
    ): Response<CurrentWeatherResponse>

}