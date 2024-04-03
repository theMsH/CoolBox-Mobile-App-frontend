package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureStatsResponse
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit = clientCreater()

val consumptionApiService = retrofit.create(ConsumptionApi::class.java)

interface ConsumptionApi {

    // Hae päivittäinen kulutustiedot seitsemältä viimeiseltä päivältä tunneittain annetun päivämäärän perusteella
    @GET("/api/measurement/consumption/total/seven_days_before_date/{date}")
    suspend fun getDailyConsumptionsDataFromLastSevenDaysByHours(@Path("date") date: String): ConsumptionStatsResponse

    // Hae tunneittainen kulutustiedot annetun päivämäärän perusteella
    @GET("/api/measurement/consumption/total/hourly/{date}")
    suspend fun getHourlyConsumptionsData(@Path("date") date: String): ConsumptionStatsResponse

    // Hae päivittäinen kulutustiedot annetun viikon päivämäärän perusteella
    @GET("/api/measurement/consumption/total/daily/week/{date}")
    suspend fun getDailyConsumptionsData(@Path("date") date: String): ConsumptionStatsResponse

    // Hae viikoittainen kulutustiedot annetun kuukauden päivämäärän perusteella
    @GET("/api/measurement/consumption/total/daily/month/{date}")
    suspend fun getWeeklyConsumptionsData(@Path("date") date: String): ConsumptionStatsResponse

    // Hae tunneittainen sisälämpötilatiedot annetun päivämäärän perusteella
    @GET("/api/measurement/temperature/indoor/avg/hourly/{date}")
    suspend fun getHourlyTemperaturesData(@Path("date") date: String): TemperatureStatsResponse
}
