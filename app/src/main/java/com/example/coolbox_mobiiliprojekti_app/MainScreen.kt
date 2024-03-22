package com.example.coolbox_mobiiliprojekti_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onMenuClick: () -> Unit) {
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
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            when {
                else -> LazyColumn(modifier = Modifier.fillMaxSize()) {}
            }
        }
    }
}