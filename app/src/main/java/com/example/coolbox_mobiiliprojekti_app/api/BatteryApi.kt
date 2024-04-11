package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.BatteryStatsResponse
import retrofit2.http.GET

val batteryApiService: BatteryApiService = retrofit.create(BatteryApiService::class.java)

// Rajapinta akkutietojen hakemiseen Web-API:sta:
interface  BatteryApiService {
    @GET("measurement/battery/current")
    suspend fun getMostRecentValuesFromBattery(): BatteryStatsResponse
}