package com.example.coolbox_mobiiliprojekti_app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.coolbox_mobiiliprojekti_app.model.PanelPreferences
import kotlinx.coroutines.flow.map

class UserPreferences( private val context: Context) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
    var pref = context.dataStore

    companion object {
        var consumptionActive = booleanPreferencesKey("CONSUMPTION_CHECKED")
        var productionActive = booleanPreferencesKey("PRODUCTION_ACTIVE")
        var batteryActive = booleanPreferencesKey("BATTERY_ACTIVE")
    }

    suspend fun setPreferences(panelPreferences : PanelPreferences) {
        pref.edit {
            it[consumptionActive] = panelPreferences.consumptionActive
            it[productionActive] = panelPreferences.productionActive
            it[batteryActive] = panelPreferences.batteryActive
        }
    }

    fun getPreferences() = pref.data.map {
        PanelPreferences(
            consumptionActive = it[consumptionActive]?:true,
            productionActive = it[productionActive]?:true,
            batteryActive = it[batteryActive]?:true
        )
    }
}