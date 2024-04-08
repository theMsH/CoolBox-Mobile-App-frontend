package com.example.coolbox_mobiiliprojekti_app.api

import com.example.coolbox_mobiiliprojekti_app.model.ProductionStatsResponse
import retrofit2.http.GET
import retrofit2.http.Path

val productionApiService: ProductionApiService = retrofit.create(ProductionApiService::class.java)

interface ProductionApiService {

    // Hae päivittäiset kulutustiedot seitsemältä viimeiseltä päivältä tunneittain annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/production/total/seven_day_period/{date}")
    suspend fun getSevenDayProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae tunneittaiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/production/total/hourly/{date}")
    suspend fun getHourlyProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae päivittäiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/production/total/daily/week/{date}")
    suspend fun getDailyProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae viikoittaiset kulutustiedot annetun kuukauden päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/production/total/daily/month/{date}")
    suspend fun getWeeklyProductionsData(@Path("date") date: String): ProductionStatsResponse

    // Hae kuukausittaiset kulutustiedot annetun päivämäärän perusteella.
    // @param date: Päivämäärä merkkijonona muodossa "YYYY-MM-DD".
    // @return ProductionStatsResponse: Vastaus, joka sisältää kulutustiedot.
    @GET("measurement/production/total/monthly/{date}")
    suspend fun getMonthlyProductionsData(@Path("date") date: String): ProductionStatsResponse

}
