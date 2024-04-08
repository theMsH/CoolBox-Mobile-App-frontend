package com.example.coolbox_mobiiliprojekti_app.model

data class MainScreenState(
    val loading: Boolean = false
)

data class PanelPreferences(
    var consumptionActive : Boolean,
    var productionActive : Boolean,
    var batteryActive : Boolean
)