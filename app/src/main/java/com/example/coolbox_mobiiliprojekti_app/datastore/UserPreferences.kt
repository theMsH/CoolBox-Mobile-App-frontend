package com.example.coolbox_mobiiliprojekti_app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class UserPreferences(context: Context) {


    var pref = context.dataStore

    var consumptionActive = booleanPreferencesKey("CONSUMPTION_CHECKED")
    var productionActive = booleanPreferencesKey("PRODUCTION_ACTIVE")
    var batteryActive = booleanPreferencesKey("BATTERY_ACTIVE")

    // Haetaan booleanit dataStoresta
    var getConsumptionActive = flow<Boolean> {
        pref.data.map {
            it[consumptionActive]?:true
        }.collect(collector = {
            emit(it)
        })
    }

    var getProductionActive = flow<Boolean> {
        pref.data.map {
            it[productionActive]?:true
        }.collect(collector = {
            emit(it)
        })
    }

    var getBatteryActive = flow<Boolean> {
        pref.data.map {
            it[batteryActive]?:true
        }.collect(collector = {
            emit(it)
        })
    }

    // Asetetaan booleanit annettuun arvoon
    suspend fun setConsumptionActive(state : Boolean) {
        pref.edit {
            it[consumptionActive] = state
        }
    }

    suspend fun setProductionActive(state : Boolean) {
        pref.edit {
            it[productionActive] = state
        }
    }

    suspend fun setBatteryActive(state : Boolean) {
        pref.edit {
            it[batteryActive] = state
        }
    }
}