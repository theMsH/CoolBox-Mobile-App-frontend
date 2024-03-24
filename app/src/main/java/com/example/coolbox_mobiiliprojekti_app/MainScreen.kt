package com.example.coolbox_mobiiliprojekti_app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coolbox_mobiiliprojekti_app.viewmodel.MainScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onMenuClick: () -> Unit,
    gotoConsumption: () -> Unit
) {
    val mainScreenVm: MainScreenViewModel = viewModel()
    Scaffold (
        topBar =
        { TopAppBar(
            navigationIcon = {
                IconButton(onClick = { onMenuClick() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                } },
            title = { Text(text = "Main") })
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            when {
                mainScreenVm.mainScreenState.value.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center)
                )
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item{
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Blue)
                            .clickable(onClick = gotoConsumption)
                        ) {
                            Text(text = "Graafi 1")
                        }
                    }
                    item{
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Green)
                        ) {
                            Text(text = "Graafi 2")
                        }
                    }
                }
            }
        }
    }
}