package com.example.coolbox_mobiiliprojekti_app.model

import com.google.gson.annotations.SerializedName

data class SolarChartState(
    val loading: Boolean = false,
    val error: String? = null
)

data class SolarStatsResponse(
    val data: List<WindStatsData>
)

data class SolarStatsData(
    val date: String = "",
    val day: String = "",
    val hour: String = "",
    val month: String = "",
    @SerializedName("total_kwh")
    val totalKwh: Float,
)
