package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.BatteryStatsResponse
import retrofit2.http.GET
import retrofit2.http.Path

val batteryApiService = retrofit.create(BatteryApiService::class.java)

// Rajapinta akkutietojen hakemiseen Web-API:sta:
interface  BatteryApiService {
    @GET("measurement/battery/current/{date}")
    suspend fun getMostRecentValuesFromBattery(@Path("date") date: String): BatteryStatsResponse
}