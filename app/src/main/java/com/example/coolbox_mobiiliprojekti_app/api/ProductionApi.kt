package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.ProductionStatsResponse
import retrofit2.http.GET
import retrofit2.http.Path

val productionApiService: ProductionApiService = retrofit.create(ProductionApiService::class.java)

interface ProductionApiService {
    @GET("measurement/production/total/seven_day_period/{date}")
    suspend fun getSevenDayProductionsData(@Path("date") date: String): ProductionStatsResponse
}