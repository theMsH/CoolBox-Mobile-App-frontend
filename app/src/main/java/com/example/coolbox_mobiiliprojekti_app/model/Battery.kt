package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName

// Tämä luokka kuvaa kulutustietojen vastausta, joka sisältää kulutusdatan.
data class BatteryStatsResponse(
    @SerializedName("current_battery_stats")
    val currentBatteryStats: List<BatteryData> // Lista akkudatan alkioista.
)

// Tämä luokka edustaa yksittäistä kulutustietoa.
data class BatteryData(
    val sensor: String, // Akun sensorin nimi
    val value: Float // Sensorin tuottama arvo
)