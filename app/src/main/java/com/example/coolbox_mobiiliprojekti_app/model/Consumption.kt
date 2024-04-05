package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName


// Tila, joka kuvaa kaavion tilaa, kuten latausta ja virhetilaa.
data class ChartState(
    val loading: Boolean = false, // Ilmaisee, onko kaavio lataamassa tietoja.
    val error: String? = null // Virheteksti, jos kaavion tiedon lataaminen epäonnistuu.
)

// Tämä luokka kuvaa kulutustietojen vastausta, joka sisältää kulutusdatan.
data class ConsumptionStatsResponse(
    val data: List<ConsumptionData> // Kulutusdatan lista.
)

// Tämä luokka edustaa yksittäistä kulutustietoa.
data class ConsumptionData(
    val date: String = "", // Päivämäärä, jolloin kulutus on tapahtunut.
    val day: String = "", // Kulutuksen päivä.
    val hour: String = "", // Kulutuksen tunti.
    val month: String = "", // Kulutuksen kuukausi.
    @SerializedName("total_kwh")
    val totalKwh: Float, // Kokonaiskulutus kilowattitunteina.
)
