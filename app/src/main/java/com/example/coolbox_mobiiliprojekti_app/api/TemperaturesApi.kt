package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.BatteryStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperaturesStatsResponse
import retrofit2.http.GET

val temperaturesApiService: TemperaturesApiService = retrofit.create(TemperaturesApiService::class.java)

// Rajapinta akkutietojen hakemiseen Web-API:sta:
interface  TemperaturesApiService {
    @GET("measurement/temperature/currents")
    suspend fun getMostRecentValuesFrom4DifferentTemperatures(): TemperaturesStatsResponse
}