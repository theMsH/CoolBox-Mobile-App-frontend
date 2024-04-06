package com.example.coolbox_mobiiliprojekti_app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.coolbox_mobiiliprojekti_app.model.MainScreenState

class MainScreenViewModel : ViewModel() {
    private val _mainScreenState = mutableStateOf(MainScreenState())
    val mainScreenState: State<MainScreenState> = _mainScreenState

    var conPanelVisible = true
    var prodPanelVisible = true
    var batPanelVisible = true
    var tempPanelVisible = true
}