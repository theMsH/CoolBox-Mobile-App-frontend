package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName

// Tämä on TemperatureStatsResponse-luokka, joka sisältää lämpötilatietoja.
data class TemperatureStatsResponse(
    val data: List<TemperatureStatsData> // Data sisältää lämpötiladatan listana.
)

// Tämä on TemperatureStatsData-luokka, joka sisältää yksittäisen lämpötiladatan.
data class TemperatureStatsData(
    val date: String = "", // Päivämäärä
    val day: String = "", // Päivä
    val hour: String = "", // Tunti
    val month: String = "", // Kuukausi
    @SerializedName("avg_C")
    val temperature: Float, // Kokonaiskulutus kilowattitunteina
    val error: String? = null, // Virheviesti (mahdollinen)
)

// Tämä on TemperatureStatsResponse-luokka, joka sisältää keskiarvon lämpötilatiedoista.
data class TemperatureAvgStatsResponse(
    val data: List<TemperatureAvgStatsData> // Data sisältää lämpötiladatan listana.
)
data class TemperatureAvgStatsData(
    @SerializedName("avg_C")
    val temperature: Float, // Kokonaiskulutus kilowattitunteina
    val error: String? = null, // Virheviesti (mahdollinen)
)


