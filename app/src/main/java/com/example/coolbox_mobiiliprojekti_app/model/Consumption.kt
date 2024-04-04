package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName


data class ChartState(
    val loading: Boolean = false,
    val error: String? = null
)

// Tämä on ConsumptionStatsResponse-luokka, joka sisältää kulutustietoja.
data class ConsumptionStatsResponse(
    val data: List<ConsumptionData> // Data sisältää kulutusdatan listana.
)

// Tämä on TemperatureStatsResponse-luokka, joka sisältää lämpötilatietoja.
data class TemperatureStatsResponse(
    val data: List<TemperatureStatsData> // Data sisältää lämpötiladatan listana.
)

// Tämä on ConsumptionData-luokka, joka sisältää yksittäisen kulutusdatan.
data class ConsumptionData(
    val date: String = "", // Päivämäärä
    val day: String = "", // Päivä
    val hour: String = "", // Tunti
    @SerializedName("total_kwh")
    val totalKwh: Float, // Kokonaiskulutus kilowattitunteina
)

// Tämä on TemperatureStatsData-luokka, joka sisältää yksittäisen lämpötiladatan.
data class TemperatureStatsData(
    val hour: String = "", // Tunti
    val temperature: Float, // Lämpötila
    val error: String? = null, // Virheviesti (mahdollinen)
)
