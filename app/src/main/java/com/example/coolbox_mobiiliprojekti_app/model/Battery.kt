package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName

data class BatteryChartState(
    // arvo ilmaisee, ovatko graafin tiedot latautumassa
    val loading: Boolean = false,
    // indikoi graafin latautumisen onnistumista
    val error: String? = null,
    // arvo indikoi akun varausta:
    val soc: Float = 0f,
    // arvo indikoi akun lämpötilaa:
    val temp: Float = 0f,
    // arvo indikoi akun jännitettä:
    val voltage: Float = 0f
)

// Akkudatan responsen rakenne:
data class BatteryStatsResponse(
    // Lista akkudatan alkioista:
    @SerializedName("current_battery_stats")
    val currentBatteryStats: List<BatteryData>
)

// Akku-responsen yksittäinen alkio:
data class BatteryData(
    val sensor: String, // Akun sensorin nimi
    val value: Float // Sensorin tuottama arvo
)