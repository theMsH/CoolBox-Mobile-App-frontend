package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName

// Tila, joka kuvaa kaavion tilaa, kuten latausta ja virhetilaa.
data class ProductionChartState(
    val loading: Boolean = false, // Ilmaisee, onko kaavio lataamassa tietoja.
    val error: String? = null // Virheteksti, jos kaavion tiedon lataaminen epäonnistuu.
)

// Tämä luokka kuvaa tuottotietojen vastausta, joka sisältää seitsemän edellispäivän tuottodatan.
data class ProductionStatsResponse(
    val data: List<ProductionData> // Kulutusdatan lista.
)

// Tämä luokka edustaa yksittäistä kulutustietoa.
data class ProductionData(
    val date: String = "", // Päivämäärä, jolloin tuotto on tapahtunut.
    val day: String = "", // Tuottopäivä.
    val hour: String = "", // Tuoton tunti.
    val month: String = "", // Tuoton kuukausi.
    @SerializedName("total_kwh")
    val totalKwh: Float, // Kokonaistuotto kilowattitunteina.
)

// Tämä luokka kuvaa tuottotietojen vastausta, joka sisältää seitsemän edellispäivän tuottodatan.
data class ProductionAvgStatsResponse(
    val data: List<ProductionAvgData>
)

data class ProductionAvgData(
    @SerializedName("avg_kwh")
    val avgKwh: Float, // Keskiarvo kilowattitunteina.
)
