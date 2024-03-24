package com.example.coolbox_mobiiliprojekti_app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.ConsumptionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumptionScreen(goBack: () -> Unit) {
    val consumptionVm: ConsumptionViewModel = viewModel()

    Scaffold (
        topBar =
        { TopAppBar(
            navigationIcon = {
                IconButton(onClick = { goBack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowLeft, contentDescription = "Back")
                } },
            title = { Text(text = "Consumption") })
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item{
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Blue)
                    ) {
                        Text(text = "Kulutus")
                    }
                }
            }
        }
    }
}