package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionAvgStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.ConsumptionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureAvgStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureStatsResponse
import retrofit2.http.GET
import retrofit2.http.Path

val consumptionApiService = retrofit.create(ConsumptionApiService::class.java)

// Rajapinta kulutustietojen hakemiseen verkkopalvelusta.
interface ConsumptionApiService {

    // Hae päivittäiset kulutustiedot seitsemältä viimeiseltä päivältä tunneittain annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ConsumptionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/consumption/total/seven_day_period/{date}")
    suspend fun getSevenDayConsumptionsData(@Path("date") date: String): ConsumptionStatsResponse

    // Hae tunneittaiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ConsumptionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/consumption/total/hourly/{date}")
    suspend fun getHourlyConsumptionsData(@Path("date") date: String): ConsumptionStatsResponse

    // Hae päivittäiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ConsumptionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/consumption/total/daily/week/{date}")
    suspend fun getDailyConsumptionsData(@Path("date") date: String): ConsumptionStatsResponse

    // Hae viikoittaiset kulutustiedot annetun kuukauden päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ConsumptionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/consumption/total/daily/month/{date}")
    suspend fun getWeeklyConsumptionsData(@Path("date") date: String): ConsumptionStatsResponse

    // Hae kuukausittaiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ConsumptionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/consumption/total/monthly/{date}")
    suspend fun getMonthlyConsumptionsData(@Path("date") date: String): ConsumptionStatsResponse


    // Hae keskiarvo tunneittaisesta kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/consumption/total/avg/day/{date}}")
    suspend fun getAvgHourlyConsumptionsData(@Path("date") date: String): ConsumptionAvgStatsResponse

    // Hae keskiarvo päivittäiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/consumption/total/avg/week/{date}")
    suspend fun getAvgDailyConsumptionsData(@Path("date") date: String): ConsumptionAvgStatsResponse

    // Hae keskiarvo viikoittaiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/consumption/total/avg/month/{date}")
    suspend fun getAvgWeeklyConsumptionsData(@Path("date") date: String): ConsumptionAvgStatsResponse

    // Hae keskiarvo kuukausittaiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/consumption/total/avg/year/{date}")
    suspend fun getAvgMonthlyConsumptionsData(@Path("date") date: String): ConsumptionAvgStatsResponse


    // Hae tunneittaiset sisälämpötilatiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/temperature/avg/indoor/hourly/{date}")
    suspend fun getHourlyTemperaturesData(@Path("date") date: String): TemperatureStatsResponse

    // Hae päivittäiset sisälämpötilatiedot annetun viikon päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/temperature/avg/indoor/daily/week/{date}")
    suspend fun getDailyTemperaturesData(@Path("date") date: String): TemperatureStatsResponse

    // Hae viikoittaiset sisälämpötilatiedot annetun kuukauden päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/temperature/avg/indoor/daily/month/{date}")
    suspend fun getWeeklyTemperaturesData(@Path("date") date: String): TemperatureStatsResponse

    // Hae kuukausittaiset sisälämpötilatiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/temperature/avg/indoor/monthly/{date}")
    suspend fun getMonthlyTemperaturesData(@Path("date") date: String): TemperatureStatsResponse


    @GET("measurement/temperature/avg/indoor/seven_day_period/{date}")
    suspend fun getSevenDayTemperaturesData(@Path("date") date: String): TemperatureStatsResponse


    // Hae keskiarvo tunneittaisesta sisälämpötilatiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/temperature/avg/indoor/hourly/{date}")
    suspend fun getAvgHourlyTemperaturesData(@Path("date") date: String): TemperatureAvgStatsResponse

    // Hae keskiarvo päivittäiset sisälämpötilatiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/temperature/avg/indoor/daily/{date}")
    suspend fun getAvgDailyTemperaturesData(@Path("date") date: String): TemperatureAvgStatsResponse

    // Hae keskiarvo viikoittaiset sisälämpötilatiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/temperature/avg/indoor/monthly/{date}")
    suspend fun getAvgWeeklyTemperaturesData(@Path("date") date: String): TemperatureAvgStatsResponse

    // Hae keskiarvo kuukausittaiset sisälämpötilatiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/temperature/avg/indoor/monthly/{date}")
    suspend fun getAvgMonthlyTemperaturesData(@Path("date") date: String): TemperatureAvgStatsResponse

}
