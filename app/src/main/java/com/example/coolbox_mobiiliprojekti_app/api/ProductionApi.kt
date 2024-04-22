package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.ProductionAvgStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.ProductionStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.SolarStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.TemperatureStatsResponse
import com.example.coolbox_mobiiliprojekti_app.model.WindStatsResponse
import retrofit2.http.GET
import retrofit2.http.Path

val productionApiService: ProductionApiService = retrofit.create(ProductionApiService::class.java)

interface ProductionApiService {

    // Hae päivittäiset tuottotiedot seitsemältä viimeiseltä päivältä tunneittain annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuottotiedot.
    @GET("measurement/production/total/seven_day_period/{date}")
    suspend fun getSevenDayProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae tunneittaiset tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuottotiedot.
    @GET("measurement/production/total/hourly/{date}")
    suspend fun getHourlyProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae päivittäiset tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuottotiedot.
    @GET("measurement/production/total/daily/week/{date}")
    suspend fun getDailyProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae viikoittaiset tuottotiedot annetun kuukauden päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuottotiedot.
    @GET("measurement/production/total/daily/month/{date}")
    suspend fun getWeeklyProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae kuukausittaiset tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuottotiedot.
    @GET("measurement/production/total/monthly/{date}")
    suspend fun getMonthlyProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae keskiarvo tunneittaisesta tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/production/total/avg/day/{date}")
    suspend fun getAvgHourlyProductionsData(@Path("date") date: String): ProductionAvgStatsResponse

    // Hae keskiarvo päivittäiset tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/production/total/avg/week/{date}")
    suspend fun getAvgDailyProductionsData(@Path("date") date: String): ProductionAvgStatsResponse

    // Hae keskiarvo viikoittaiset tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/production/total/avg/month/{date}")
    suspend fun getAvgWeeklyProductionsData(@Path("date") date: String): ProductionAvgStatsResponse

    // Hae keskiarvo kuukausittaiset tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return TemperatureStatsResponse: Vastaus, joka sisältää sisälämpötilatiedot.
    @GET("measurement/production/total/avg/year/{date}")
    suspend fun getAvgMonthlyProductionsData(@Path("date") date: String): ProductionAvgStatsResponse



    // Hae kuukausittaiset tuuli generaattorin tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuuli generaattorin tuottotiedot.
    @GET("measurement/wind/total_kwh/wind_production/seven_day_period/{date}")
    suspend fun getSevenDayWindProductionsData(@Path("date") date: String): WindStatsResponse

    // Hae kuukausittaiset tuuli generaattorin tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuuli generaattorin tuottotiedot.
    @GET("measurement/wind/total_kwh/wind_production/hourly/{date}")
    suspend fun getHourlyWindProductionsData(@Path("date") date: String): WindStatsResponse

    // Hae kuukausittaiset tuuli generaattorin tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuuli generaattorin tuottotiedot.
    @GET("measurement/wind/total_kwh/wind_production/daily/week/{date}")
    suspend fun getDailyWindProductionsData(@Path("date") date: String): WindStatsResponse

    // Hae kuukausittaiset tuuli generaattorin tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuuli generaattorin tuottotiedot.
    @GET("measurement/wind/total_kwh/wind_production/daily/month/{date}")
    suspend fun getWeeklyWindProductionsData(@Path("date") date: String): WindStatsResponse

    // Hae kuukausittaiset tuuli generaattorin tuottotiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää tuuli generaattorin tuottotiedot.
    @GET("measurement/wind/total_kwh/wind_production/monthly/{date}")
    suspend fun getMonthlyWindProductionsData(@Path("date") date: String): WindStatsResponse

    @GET("measurement/solar/total/seven_day_period/{date}")
    suspend fun getSevenDaySolarProductionData(@Path("date") date: String): SolarStatsResponse

    @GET("measurement/solar/total/hourly/{date}")
    suspend fun getHourlySolarProductionData(@Path("date") date: String): SolarStatsResponse

    @GET("measurement/solar/total/daily/week/{date}")
    suspend fun getDailyByWeekSolarProductionData(@Path("date") date: String): SolarStatsResponse

    @GET("measurement/solar/total/daily/month/{date}")
    suspend fun getDailyByMonthSolarProductionData(@Path("date") date: String): SolarStatsResponse

    @GET("measurement/solar/total/monthly/{date}")
    suspend fun getMonthlySolarProductionData(@Path("date") date: String): SolarStatsResponse
}
