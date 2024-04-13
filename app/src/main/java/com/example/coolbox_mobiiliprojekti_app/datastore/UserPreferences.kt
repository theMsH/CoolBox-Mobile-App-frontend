package com.example.coolbox_mobiiliprojekti_app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

// DataStoresta saa olla olemassa kerralla vain yksi instanssi,
// joten se luodaan kotlin-tiedoston ylimmässä osassa
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

class UserPreferences(context: Context) {

    // lyhyt muuttuja usein käytetylle parametrille, jolla kutsutaan DataStorea
    var pref = context.dataStore
    // Muuttujat, jotka pitävät sisällään boolean-arvoja
    var consumptionActive = booleanPreferencesKey("CONSUMPTION_ACTIVE")
    var productionActive = booleanPreferencesKey("PRODUCTION_ACTIVE")
    var batteryActive = booleanPreferencesKey("BATTERY_ACTIVE")
    var tempActive = booleanPreferencesKey("TEMP_ACTIVE")

    // Haetaan booleanit dataStoresta
    var getConsumptionActive = flow {
        pref.data.map {
            it[consumptionActive]?:true // jos arvo on null, aseta arvoksi true
        }.collect(collector = {
            emit(it) //flow nappaa avaimen sisältämän arvon ja lähettää sen var:in arvona
        })
    }

    var getProductionActive = flow {
        pref.data.map {
            it[productionActive]?:true
        }.collect(collector = {
            emit(it)
        })
    }

    var getBatteryActive = flow {
        pref.data.map {
            it[batteryActive]?:true
        }.collect(collector = {
            emit(it)
        })
    }

    var getTempActive = flow {
        pref.data.map {
            it[tempActive]?:true
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

    suspend fun setTempActive(state : Boolean) {
        pref.edit {
            it[tempActive] = state
        }
    }
}