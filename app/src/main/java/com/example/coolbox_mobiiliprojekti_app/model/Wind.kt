package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName

// Tila, joka kuvaa kaavion tilaa, kuten latausta ja virhetilaa.
data class WindChartState(
    val loading: Boolean = false, // Ilmaisee, onko kaavio lataamassa tietoja.
    val error: String? = null // Virheteksti, jos kaavion tiedon lataaminen epäonnistuu.
)

// Tämä on WindStatsResponse-luokka, joka sisältää lämpötilatietoja.
data class WindStatsResponse(
    val data: List<WindStatsData> // Data sisältää lämpötiladatan listana.
)

// Tämä on WindStatsData-luokka, joka sisältää yksittäisen lämpötiladatan.
data class WindStatsData(
    val date: String = "", // Päivämäärä
    val day: String = "", // Päivä
    val hour: String = "", // Tunti
    val month: String = "", // Kuukausi
    @SerializedName("total_kwh")
    val totalKwh: Float, // Kokonaistuotto kilowattitunteina.
)
